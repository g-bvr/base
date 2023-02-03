package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.*;
import org.jkube.gitbeaver.interfaces.Command;
import org.jkube.gitbeaver.interfaces.Plugin;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.util.Expect;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DokumentationCommand extends AbstractCommand {

    private static final String HTML_HEADER = """
            <!DOCTYPE html>
            <html>
            <head>
            <style>
            body {
              background-color: #FAFFFA;
            }
            #myheader {
              font-size: 60px;
              font-weight: 600;
              color: #3d3d3e;
              text-shadow: 0px 0px 5px #EEEEEE, 0px 0px 10px #EEEEEE, 0px 0px 10px #EEEEEE,
                0px 0px 20px #EEEEEE;
            }
            #mytable {
              font-family: Arial, Helvetica, sans-serif;
              border-collapse: collapse;
              width: 100%;
            }
            #mytable td, #mytable th {
              border: 1px solid #ddd;
              padding: 8px;
            }
            #mytable tr:nth-child(even){background-color: #f2f2e2;}
            #mytable tr:nth-child(odd){background-color: #e2f2f2;}
            #mytable tr:hover {background-color: #ddd;}
            #mytable th {
              padding-top: 12px;
              padding-bottom: 12px;
              text-align: left;
              background-color: #04AA6D;
              color: white;
            }
            </style>
            </head>
            <body>
            """;

    private static final String HTML_FOOTER = """     
            </body>
            </html>
            """;

    private static final String PLUGIN = "plugin";
    private static final String FOLDER = "folder";
    private static final String HTML = ".html";

    public DokumentationCommand() {
        super( "Create a set of html file for documentation.");
        commandlineVariant("DOCUMENT "+PLUGIN+" INTO "+FOLDER, "Create documentation for specified plugin");
        commandlineVariant("DOCUMENT ALL PLUGINS INTO "+FOLDER, "Create documentation for all enabled plugins");
        argument(PLUGIN, "name of the plugin to be documented");
        argument(FOLDER, "path of folder (relative to current workspace) into which the documentation will be stored, the folder (and its ancestors) will be created if not existent");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        Path folder = workSpace.getAbsolutePath(arguments.get(FOLDER));
        FileUtil.createIfNotExists(folder);
        createDocumentation(folder, arguments.get(PLUGIN));
    }

    private void createDocumentation(Path folder, String plugin) {
        if (plugin == null) {
            createAllPluginsDocumentation(folder, GitBeaver.pluginManager().getAllPlugins());
        } else {
            createPluginDocumentation(folder, GitBeaver.pluginManager().getPlugin(plugin));
        }
    }

    private void createPluginDocumentation(Path folder, Plugin plugin) {
        Expect.notNull(plugin).elseFail("No such plugin found in: "+GitBeaver.pluginManager().getAllPlugins());
        String name = plugin.getClass().getSimpleName();
        FileUtil.store(folder.resolve(name+HTML), createPluginPage(name, plugin));
        plugin.getCommands().forEach(c -> createCommandDocumentation(folder, c, name));
    }

    private void createCommandDocumentation(Path folder, Command command, String pluginName) {
        String name = command.getClass().getSimpleName();
        FileUtil.store(folder.resolve(name+HTML), createCommandPage(name, command, pluginName));
    }

    private List<String> createPluginPage(String name, Plugin plugin) {
        List<String> result = new ArrayList<>();
        result.add(HTML_HEADER);
        result.add("<h1 id=\"myheader\">"+name+"</h1>");
        result.add("<h2>Purpose</h2>");
        result.add(plugin.getDescription());
        result.add("<h2>Commands</h2>");
        result.add("<table id=\"mytable\">");
        result.add("<tr><th>Command</th><th>Description</td></tr>");
        plugin.getCommands().forEach(c -> result.add(createTableRow(c)));
        result.add("</table>");
        result.add(HTML_FOOTER);
        return result;
    }

    private List<String> createCommandPage(String name, Command command, String pluginName) {
        List<String> result = new ArrayList<>();
        result.add(HTML_HEADER);
        result.add("<h1 id=\"myheader\">"+name+"</h1>");
        result.add("<h2>Purpose</h2>");
        result.add(command.getDescription());
        result.add("<h2>Variants</h2>");
        result.add("<table id=\"mytable\">");
        result.add("<tr><th>Usage</th><th>Description</td></tr>");
        command.getCommandPatterns().forEach(p -> result.add(createTableRow(p)));
        result.add("</table id=\"mytable\">");
        result.add("");
        result.add("<h2>Arguments</h2>");
        result.add("");
        result.add("<table id=\"mytable\">");
        result.add("<tr><th>Argument</th><th>Value</td></tr>");
        command.getArguments().forEach((k, v) -> result.add(createTableRow(k, v)));
        result.add("</table>");
        result.add("<h2>Plugin</h2>");
        result.add("This command is defined in "+hyperlink(pluginName+HTML, pluginName));
        result.add(HTML_FOOTER);
        return result;
    }

    private String createTableRow(Command command) {
        String name = command.getClass().getSimpleName();
        return createTableRow(name+HTML, name, command.getDescription());
    }

    private String createTableRow(CommandPattern pattern) {
        return createTableRow(pattern.getText(), pattern.getDescription());
    }

    private String createTableRow(String reference, String name, String description) {
        return createTableRow(hyperlink(reference, name), description);
    }

    private String hyperlink(String reference, String name) {
        return "<a href=\"" + reference + "\">" + name + "</a>";
    }

    private String createTableRow(String col1, String col2) {
        return "  <tr><td>"+col1+"</td><td>"+col2+"</td></tr>";
    }

    private void createAllPluginsDocumentation(Path folder, Map<String, Plugin> allPlugins) {
    }

}
