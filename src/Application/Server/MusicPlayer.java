package Application.Server;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.TargetDataLine;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class MusicPlayer
{
	private static InputStream in;
	private static AudioStream audio;
	
////////////////////////KONSTRUKTOR///////////////////////////////	
		
	public MusicPlayer() throws IOException
	{
		String wavFile="./data/Musik/Lieder/Soldier.wav";
		in = new FileInputStream(wavFile);
		audio = new AudioStream(in);
		AudioPlayer.player.start(audio);	
	}
	
	
	public MusicPlayer(String wort) throws IOException
	{
		String wavFile="./data/Musik/Lieder/Fail.wav";
		in = new FileInputStream(wavFile);
		audio = new AudioStream(in);
		AudioPlayer.player.start(audio);	
		
	}
	
//////////////////////////MAIN-METHODE/////////////////////////////////
	
	public static void main(String[]args) throws IOException, InterruptedException
	{	
		MusicPlayer music = new MusicPlayer();
		MusicPlayer music2 = new MusicPlayer("fail");
	}
}
	
