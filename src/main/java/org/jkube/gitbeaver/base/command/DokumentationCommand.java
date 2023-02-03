package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.*;
import org.jkube.gitbeaver.interfaces.Command;
import org.jkube.gitbeaver.interfaces.Plugin;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.logging.Log;
import org.jkube.util.Expect;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
              text-shadow: 0px 0px 5px #DDDDDD, 0px 0px 10px #DDDDDD, 0px 0px 10px #EEEEEE,
                0px 0px 20px #EEEEEE;
            }
            #mytable1 {
              font-family: Arial, Helvetica, sans-serif;
              border-collapse: collapse;
              width: 100%;
            }
            #mytable1 td, #mytable1 th {
              border: 1px solid #ddd;
              padding: 8px;
            }
            #mytable1 tr:nth-child(even){background-color: #f2f2e2;}
            #mytable1 tr:nth-child(odd){background-color: #e2f2f2;}
            #mytable1 tr:hover {background-color: #ddd;}
            #mytable1 th {
              padding-top: 12px;
              padding-bottom: 12px;
              text-align: left;
              background-color: #04AA6D;
              color: white;
            }
            #mytable2 {
              font-family: Arial, Helvetica, sans-serif;
              border-collapse: collapse;
              width: 100%;
            }
            #mytable2 td, #mytable2 th {
              border: 1px solid #ddd;
              padding: 8px;
            }
            #mytable2 tr:nth-child(even){background-color: #f2f2e2;}
            #mytable2 tr:nth-child(odd){background-color: #e2f2f2;}
            #mytable2 tr:hover {background-color: #ddd;}
            #mytable2 th {
              padding-top: 12px;
              padding-bottom: 12px;
              text-align: left;
              background-color: #A43A6D;
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
    private static final String RELEASE = "release";
    private static final String HTML = ".html";
    private static final String OVERVIEW = "index";

    public DokumentationCommand() {
        super( "Create a set of html file for documentation.");
        commandlineVariant("DOCUMENT "+PLUGIN+" INTO "+FOLDER, "Create documentation for specified plugin");
        commandlineVariant("DOCUMENT RELEASE "+RELEASE+" INTO "+FOLDER, "Create documentation for all enabled plugins");
        argument(PLUGIN, "name of the plugin to be documented");
        argument(RELEASE, "name of the release to be documented");
        argument(FOLDER, "path of folder (relative to current workspace) into which the documentation will be stored, the folder (and its ancestors) will be created if not existent");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        Path folder = workSpace.getAbsolutePath(arguments.get(FOLDER));
        FileUtil.createIfNotExists(folder);
        if (arguments.containsKey(PLUGIN)) {
            String plugin = arguments.get(PLUGIN);
            Log.log("Creating documentation for plugin: "+plugin);
            createPluginDocumentation(folder, GitBeaver.pluginManager().getPlugin(plugin), null);
        } else {
            String release = arguments.get(RELEASE);
            Log.log("Creating documentation for release "+release);
            createAllPluginsDocumentation(folder, release, GitBeaver.pluginManager().getAllPlugins());
        }
    }

    private void createDocumentation(Path folder, String release, String plugin) {
        if (plugin == null) {
            Log.log("Creating documentation for all plugins");
            createAllPluginsDocumentation(folder, release, GitBeaver.pluginManager().getAllPlugins());
        } else {
            Log.log("Creating documentation for plugin: "+plugin);
            createPluginDocumentation(folder, GitBeaver.pluginManager().getPlugin(plugin), release);
        }
    }

    private void createPluginDocumentation(Path folder, Plugin plugin, String release) {
        Expect.notNull(plugin).elseFail("No such plugin found in: "+GitBeaver.pluginManager().getAllPlugins());
        String name = plugin.getClass().getSimpleName();
        FileUtil.store(folder.resolve(name+HTML), createPluginPage(name, plugin, release));
        plugin.getCommands().forEach(c -> createCommandDocumentation(folder, c, name));
    }

    private void createCommandDocumentation(Path folder, Command command, String pluginName) {
        String name = command.getClass().getSimpleName();
        FileUtil.store(folder.resolve(name+HTML), createCommandPage(name, command, pluginName));
    }

    private List<String> createPluginPage(String name, Plugin plugin, String release) {
        List<String> result = new ArrayList<>();
        result.add(HTML_HEADER);
        result.add("<h1 id=\"myheader\">"+name+"</h1>");
        result.add("<h2>Purpose</h2>");
        result.add(plugin.getDescription());
        result.add("<h2>Commands</h2>");
        result.add("<table id=\"mytable1\">");
        result.add("<tr><th>Command</th><th>Description</th></tr>");
        plugin.getCommands().forEach(c -> result.add(createTableRow(c)));
        result.add("</table>");
        if (release != null) {
            result.add("<h2>Release</h2>");
            result.add("The plugin is part og release "+hyperlink(OVERVIEW+HTML, release));
        }
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
        result.add("<table id=\"mytable1\">");
        result.add("<tr><th>Usage</th><th>Description</th></tr>");
        command.getCommandPatterns().forEach(p -> result.add(createTableRow(p)));
        result.add("</table>");
        result.add("");
        result.add("<h2>Arguments</h2>");
        result.add("");
        result.add("<table id=\"mytable2\">");
        result.add("<tr><th>Argument</th><th>Value</th></tr>");
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

    private void createAllPluginsDocumentation(Path folder, String release, Map<String, Plugin> allPlugins) {
        allPlugins.keySet().forEach(p -> createDocumentation(folder, release, p));
        FileUtil.store(folder.resolve(OVERVIEW+HTML), createOverviewPage(release, allPlugins));
    }

    private List<String> createOverviewPage(String release, Map<String, Plugin> plugins) {
        List<String> result = new ArrayList<>();
        result.add(HTML_HEADER);
        result.add("<h1 id=\"myheader\">"+release+"</h1>");
        result.add("<h2>Plugins</h2>");
        result.add("<table id=\"mytable1\">");
        result.add("<tr><th>Plugin</th><th>Purpose</th></tr>");
        plugins.values().forEach(p -> result.add(createTableRow(p)));
        result.add("</table>");
        result.add("");
        result.add("<h2>Commands</h2>");
        result.add("");
        result.add("<table id=\"mytable2\">");
        result.add("<tr><th>Command</th><th>Purpose</th></tr>");
        Map<String, Command> allCommands = new TreeMap<>();
        plugins.values().forEach(p -> p.getCommands().forEach(c -> allCommands.put(c.getClass().getSimpleName(), c)));
        allCommands.values().forEach(c -> result.add(createTableRow(c)));
        result.add("</table>");
        result.add(HTML_FOOTER);
        return result;
    }

    private String createTableRow(Plugin plugin) {
        String name = plugin.getClass().getSimpleName();
        return createTableRow(hyperlink(name+HTML, name), plugin.getDescription());
    }

}
