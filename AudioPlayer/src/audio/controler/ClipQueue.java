package audio.controler;

import audio.file.controler.FileController;
import audio.player.SoundClip;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ClipQueue extends FileController implements Subscriber{
    private volatile LinkedList<File> queue = new LinkedList<>();
    private SoundClip soundClip;
    private Controller controller;

    public ClipQueue(SoundClip soundClip) {
        this.soundClip = soundClip;
    }

    public ClipQueue(){}

    public void createQueue(){
        List<File> tempList = super.getMusicList();
        for (File f : tempList) {
            queue.addFirst(f);
        }
    }

    public void print(){
        for (File file : queue) {
            System.out.print(file.getName().substring(0, file.getName().length() -3) + " ");
        }
    }

    @Override
    public void update() {
        if (!soundClip.getLastCommand().equals("stop"))
            soundClip.next();
    }
}
