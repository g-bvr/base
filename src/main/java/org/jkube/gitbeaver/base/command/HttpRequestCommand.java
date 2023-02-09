package org.jkube.gitbeaver.base.command;

import org.jkube.application.Application;
import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.ScriptExecutor;
import org.jkube.gitbeaver.SimpleCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.http.Http;
import org.jkube.http.HttpSettings;
import org.jkube.logging.Log;
import org.jkube.util.Expect;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.jkube.gitbeaver.CommandParser.REST;

public class HttpRequestCommand extends AbstractCommand {

    private final String REQUEST = "request";
    private final String BODY = "body";

    public HttpRequestCommand() {
        super("Issue a http request");
        commandlineVariant("HTTP REQUEST "+REQUEST,  "Issue a http request without according to specification contained in a file");
        commandlineVariant("HTTP REQUEST "+REQUEST+" WITH BODY "+BODY,  "Issue a http request according to specification contained in a file and body in another file");
        argument(REQUEST, "Path (relative to current workspace) to file with request specification. This file must contain a line of type \"METHOD=...\", one line of type \"URL=...\" and can have multiple lines of type \"HEADER=...:...\"");
        argument(BODY, "Path (relative to current workspace) to file with request body");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        List<String> lines = FileUtil.readLines(workSpace.getAbsolutePath(arguments.get(REQUEST)));
        String method = getSpec(lines, "METHOD");
        String url = getSpec(lines, "URL");
        String body = String.join("\n", FileUtil.readLines(workSpace.getAbsolutePath(arguments.get(BODY))));
        HttpSettings settings = new HttpSettings();
        settings.headers.putAll(getHeaders(lines));
        Log.log("Sending "+method+" request: "+url);
        Optional<String> result = switch (method) {
            case "POST" -> Http.post(settings, url, body);
            case "PUT" -> Http.put(settings, url, body);
            case "PATCH" -> Http.patch(settings, url, body);
            default -> Application.fail("No such method supported: "+method);
        };
        Expect.isTrue(result.isPresent()).elseFail("Request could not be executed");
        Log.log("Request returned: "+result);
    }

    private String getSpec(List<String> lines, String key) {
        String found = null;
        for (String line : lines) {
            String[] split = line.split("=", 2);
            Expect.equal(split.length, 2).elseFail("Expected = in line: "+line);
            if (split[0].equals(key)) {
                Expect.isNull(found).elseFail("Found multiple lines for key: "+key);
                found = split[1];
            }
        }
        Expect.notNull(found).elseFail("Did not find any line for key: "+key);
        return found;
    }

    private Map<String, String> getHeaders(List<String> lines) {
        Map<String, String> res = new LinkedHashMap<>();
        for (String line : lines) {
            String[] split = line.split("=", 2);
            if (split[0].equals("HEADER")) {
                String[] split2 = split[1].split(":", 2);
                Expect.equal(split2.length, 2).elseFail("Expected : in header line: "+line);
                res.put(split2[0], split2[1]);
            }
        }
        return res;
    }

}
