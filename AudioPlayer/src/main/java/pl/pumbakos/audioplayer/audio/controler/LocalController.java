package pl.pumbakos.audioplayer.audio.controler;

import org.jetbrains.annotations.NotNull;
import pl.pumbakos.audioplayer.audio.player.SoundClip;
import pl.pumbakos.audioplayer.audio.resources.CliError;
import pl.pumbakos.audioplayer.audio.resources.Command;
import pl.pumbakos.audioplayer.audio.resources.Regex;

import java.util.*;
import java.util.stream.Stream;

public class LocalController {
    private static final String MAP_PROCESSING = "MAP_PROCESSING";
    private final Scanner scanner = new Scanner(System.in);
    private SoundClip clip;
    private ClipQueue queue;
    private Map<Integer, String> commandMap = new HashMap<>();
    private volatile String command;
    private volatile String lastCommand;

    public LocalController() {
    }

    public static void main(String[] args) {
        LocalController lc = new LocalController();
        String s = lc.fragmentCommand("play --loop -f -g -t -y");
        System.out.println(s);
    }

    public void setProperties(ClipQueue queue, SoundClip clip) {
        this.clip = clip;
        this.queue = queue;
    }

    public void menu() {
        System.out.println("\t\t\tMENU");
        System.out.println("Enter 'play' to start playing.");
        System.out.println("Enter 'stop' to stop playing.");
        System.out.println("Enter 'pause' to pause.");
        System.out.println("Enter 'resume' to resume.");
        System.out.println("Enter 'next' for next song.");
        System.out.println("Enter 'previous' for previous song.");
        System.out.println("Enter 'list' for listing all songs.");
        System.out.println("Enter 'folder -c' to change default folder.");
        System.out.println("Enter 'help' for help.");
//        System.out.println("Enter 'queue' to see current queue");

        System.out.println("\nHere are songs from your folder, choose one:");
        clip.list();
        clip.setCurrentSong();
    }

    public void help() {
        System.out.println("Incorrect value.");
        System.out.println("Enter 'help' for help");
    }

    public void cmd() {
        lastCommand = "play";
        do {
            lastCommand = command;
            System.out.print(">> ");
            command = scanner.nextLine().toLowerCase();
            if (command.isBlank()) {
                System.out.println("Empty value.");
            }
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
                case "folder -c" -> {
                    System.out.print("Enter path to folder: ");
                    clip.setDefaultFolder(new Scanner(System.in).nextLine());
                }
//                case "queue" -> {
//                    queue.createQueue();
//                    queue.print();
//                }
                default -> help();
            }
        } while (!command.equalsIgnoreCase("exit"));
    }

    /**
     * Jeśli komenda jest jedna to zwraca ją, jeśli są dodane flagi to mapuje je i sprawdza ich poprawność
     * czy flaga jest poprawna dla komendy
     *
     * @param command command that you enter in CLI
     * @return temporary return
     */
    private String fragmentCommand(@NotNull String command) {
        String[] fragmentedCommand = command.split("\\s");

        if (fragmentedCommand.length == 1) {
            return command;
        } else {
            for (String s : fragmentedCommand) {
                if (!s.matches(Regex.COMMAND_REGEX)) {
                    return CliError.REGEX_DO_NOT_MATCH;
                }
            }

            List<String> commands = Arrays.asList(fragmentedCommand);
            Stream<String> flags = commands.stream().filter(c -> c.startsWith("-"));
            commandMap.put(0, fragmentedCommand[0]);
            flags.forEach(c -> commandMap.
                    put(commands.indexOf(c), c.replace("-", ""))
            );

            return MAP_PROCESSING;
        }
    }

    private void chooseExecuteMethod(String command) {
        String whatToDo = fragmentCommand(command);
        if (whatToDo.equals(command)) {
            executeCommand(command);
        } else {
            executeCommand(this.commandMap);
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

    private void executeCommand(Map<Integer, String> map) {
        String majorCommand = map.get(0);

        switch (getCommand(majorCommand)) {
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
                //TODO: add better logic at refactoring command into map
//                clip.setDefaultFolder();
            }
            break;
            case EXIT: {
                System.exit(0);
            }
            break;

            default:
                ;
                break;
        }
    }

    private Command getCommand(String command) {
        return Command.valueOf(command);
    }

    public String getLastCommand() {
        return lastCommand;
    }
}
