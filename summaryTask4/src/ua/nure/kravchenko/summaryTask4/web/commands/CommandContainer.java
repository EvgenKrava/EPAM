package ua.nure.kravchenko.summaryTask4.web.commands;


import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Logger logger = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commandMap = new TreeMap<>();

    static {
        commandMap.put("login", new LoginCommand());
        commandMap.put("logout", new LogoutCommand());
        commandMap.put("settings", new SettingsCommand());
        commandMap.put("noCommand", new NoCommand());
        commandMap.put("admin", new AdminAuthoriseCommand());
        commandMap.put("client", new ClientAuthoriseCommand());
        commandMap.put("users", new ShowUsersCommand());
        commandMap.put("addUser", new AddUserCommand());
        commandMap.put("addUserPage", new ShowAddUserPageCommand());
        commandMap.put("deleteUser", new DeleteUserCommand());
        commandMap.put("deleteTariff", new DeleteTariffCommand());
        commandMap.put("tariffs", new ShowTariffsCommand());
        commandMap.put("home", new ShowHomeCommand());
        commandMap.put("includeTariff", new IncludeTariffForUserCommand());
        commandMap.put("denyTariff", new DenyTariffForUserCommand());
        commandMap.put("addMoney", new AddMoneyCommand());
        commandMap.put("showTopUp", new ShowTopUpPageCommand());
        commandMap.put("addTariffPage", new ShowAddTariffPageCommand());
        commandMap.put("addTariff", new AddTariffCommand());
        commandMap.put("editTariffPage", new ShowEditTariffPageCommand());
        commandMap.put("editUserPage", new ShowEditUserPageCommand());
        commandMap.put("editTariff", new EditTariffCommand());
        commandMap.put("editUser", new EditUserCommand());
        commandMap.put("setLanguage", new SetLanguageCommand());
        commandMap.put("downloadServices", new DownloadServicesCommand());
        logger.debug("Commands created");
    }

    public static Command getCommand(String commandName) {
        if (commandName == null || !commandMap.containsKey(commandName)) {
            logger.trace("Command not found, name --> " + commandName);
            return commandMap.get("noCommand");
        }
        return commandMap.get(commandName);
    }
}
