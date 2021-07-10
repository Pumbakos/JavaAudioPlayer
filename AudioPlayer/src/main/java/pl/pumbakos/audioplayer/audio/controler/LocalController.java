package pl.pumbakos.audioplayer.audio.controler;

import org.jetbrains.annotations.NotNull;
import pl.pumbakos.audioplayer.audio.resources.CliError;
import pl.pumbakos.audioplayer.audio.resources.Command;
import pl.pumbakos.audioplayer.audio.resources.CommandFlag;
import pl.pumbakos.audioplayer.audio.resources.Regex;

import java.util.*;

import static pl.pumbakos.audioplayer.audio.resources.Command.toCommand;
import static pl.pumbakos.audioplayer.audio.resources.Command.valueOf;

public class LocalController extends Controller {
    private static final byte SINGLE_COMMAND_PROCESSING = 0;
    private static final byte FLAGGED_COMMAND_PROCESSING = 1;
    List<String> commands;
    private Map<Integer, String> commandMap = new HashMap<>();
    private Stack<String> commandStack = new Stack<>();
    private int folderParamIndex;
    private int loopOverFolderParamIndex;
    private volatile String primaryCommand;

    public LocalController() {
        super();
    }

    public static void main(String[] args) {
        LocalController lc = new LocalController();
        lc.chooseExecuteMethod("folder -t -a -c D:\\Desktop\\CODE\\JAVA\\AudioPlayer\\music\\wav\\");
//        System.out.println(s);
    }

    /**
     * Jeśli komenda jest jedna to zwraca ją, jeśli są dodane flagi to mapuje je i sprawdza ich poprawność
     * czy flaga jest poprawna dla komendy
     *
     * @param command command that you enter in CLI
     * @return temporary return
     */
    private int fragmentCommand(@NotNull String command) {
        String[] fragmentedCommand = command.split("\\s");

        if (fragmentedCommand.length == 1) {
            return SINGLE_COMMAND_PROCESSING;
        } else {
            for (String s : fragmentedCommand) {
                if (!s.matches(Regex.COMMAND_REGEX)) {
                    return CliError.SYNTAX_ERROR.getErrorCode();
                }
            }

            commands = Arrays.asList(fragmentedCommand);

            for (int i = 1; i < fragmentedCommand.length; ++i) {
                commandStack.push(fragmentedCommand[i].replace("--", "").replace("-", ""));
            }

            primaryCommand = commands.get(0);

            return FLAGGED_COMMAND_PROCESSING;
        }
    }

    private void chooseExecuteMethod(String command) {
        int whatToDo = fragmentCommand(command);
        if (whatToDo == SINGLE_COMMAND_PROCESSING) {
            executeCommand(command);
        } else {
            try {
                executeCommand(this.commandStack);
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println(CliError.CANNOT_RESOLVE_GIVEN_PARAMETER);
            }
        }
    }

    private void executeCommand(String command) {
        switch (command) {
            case "play" -> {
                clip.play();
            }
            case "stop", "exit" -> {
                clip.stop();
            }
            case "next" -> {
                clip.next();
            }
            case "previous" -> {
                clip.previous();
            }
            case "resume" -> {
                clip.resume();
            }
            case "pause" -> {
                clip.pause();
            }
            case "list" -> {
                clip.list();
            }
            default -> help();
        }
    }

    /**
     * @throws NullPointerException when no Command match
     * @see Command
     */
    private void executeCommand(Stack<String> stack) throws NullPointerException {
        Command command = toCommand(primaryCommand);
//        System.out.println(command);
        switch (Objects.requireNonNull(command)) {
            case PLAY: {
                clip.play();
            }
            case STOP: {
                clip.stop();
            }
            break;
            case PAUSE: {
                clip.pause();
            }
            break;
            case RESUME: {
                clip.resume();
            }
            break;
            case NEXT: {
                clip.next();
            }
            break;
            case PREVIOUS: {
                clip.previous();
            }
            break;
            case LIST: {
                clip.list();
            }
            break;
            case FOLDER: {
                Iterator<String> iterator = stack.iterator();

                while (iterator.hasNext()) {
                    String cmd = iterator.next();
//                    System.out.println("STOS" + stack);

                    switch (cmd) {
                        case CommandFlag.FOLDER_CHOOSE, CommandFlag.Short.FOLDER_CHOOSE: {
                            String path = commandStack.pop();
                            System.out.println("FOLDER SET TO " + path);
                            clip.setDefaultFolder(path);
                        }

                        case CommandFlag.LOOP_OVER, CommandFlag.Short.LOOP_OVER: {
                            System.out.println("TOGGLED LOOP FLAG");
                            clip.setLoopOverFolder(true);
                        }

                        default:
                            System.out.println(CliError.NO_FLAG_FOUND);
                            break;
                    }
                    iterator.remove();
                }
            }
            break;
            case EXIT: {
                System.exit(0);
            }
            break;

            default:
                System.out.println(CliError.CANNOT_RESOLVE_GIVEN_PARAMETER);
                break;
        }
    }

    private Command getCommand(String command) {
        return valueOf(command);
    }
}
