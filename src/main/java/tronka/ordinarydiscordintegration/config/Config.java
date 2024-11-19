package tronka.ordinarydiscordintegration.config;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import net.fabricmc.loader.api.FabricLoader;
import tronka.ordinarydiscordintegration.OrdinaryDiscordIntegration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {
    public static Config INSTANCE = loadConfig();

    public String botToken = "";
    public String serverChatChannel = "";
    public String consoleChannel = "";
    public boolean showCommandsInConsole = true;
    public boolean useWebHooks = true;

    public String avatarUrl = "https://minotar.net/avatar/%UUID%?randomuuid=%randomUUID%";

    public boolean unlinkOnLeave = true;

    public JoinOptions joining = new JoinOptions();

    public boolean stackMessages = false; // not implemented

    public static class JoinOptions {
        public boolean enableLinking = true;
        public long linkCodeExpireMinutes = 10;
        public List<String> requiredJoinRoles = new ArrayList<>();
        public List<String> assignRoleAtJoin = new ArrayList<>();
        public boolean renameOnJoin = true;
    }

    public ErrorStrings strings = new ErrorStrings();

    public static class ErrorStrings {
        public String kickMissingRoles = "You currently don't have the permission to join the server.";
        public String kickLinkCode = "Please Link your discord account by using\n/link %s\non discord";
    }

    public MessageStrings messages = new MessageStrings();

    public static class MessageStrings {
        public String chatMessageFormat = "[§9Discord§r] <%user%> %msg%";
        public String commandExecutedInfoText = "%user% executed ``%msg%``";
        public String playerJoinMessage = "%user% joined";
        public String playerLeaveMessage = "%user% left";
    }

    public DiscordLinkResults linkResults = new DiscordLinkResults();

    public static class DiscordLinkResults {
        public String linkSuccess = "Successfully linked to %name%";
        public String failedUnknownCode = "Unknown code, did you copy it correctly?";
        public String failedTooManyLinked = "You cannot link to another account";
    }



    private static Config loadConfig(){
        var configDir = FabricLoader.getInstance().getConfigDir();
        var configFile = configDir.resolve(OrdinaryDiscordIntegration.ModId + ".toml").toFile();
        if (configFile.exists()){
            return new Toml().read(configFile).to(Config.class);
        } else {
            var config = new Config();
            try {
                new TomlWriter().write(config, configFile);
            } catch (IOException ignored) { }
            return config;
        }

    }

}
