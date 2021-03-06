package majorissue.com.gravity.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.majorissue.game.R;

import majorissue.com.framework.Game;
import majorissue.com.framework.Graphics;
import majorissue.com.framework.Input.TouchEvent;
import majorissue.com.framework.Screen;
import majorissue.com.gravity.GravityGame;
import majorissue.com.gravity.util.Assets;
import majorissue.com.gravity.util.Settings;

public class MenuScreen extends Screen {

	public MenuScreen(Game game) {
		super(game);
	}
	
	public void playBackgroundMusic() {
		if(Settings.musicEnabled) {
			Assets.music_bkg_01.setLooping(true);
			Assets.music_bkg_01.play();
		} else {
			Assets.music_bkg_01.stop();
		}
	}
	
	protected String[][] drawMenu(String[] entries, String touchedMenuEntry) {
		
		Graphics g = game.getGraphics();
		
		int widthMenuBlock = 0;
		int heightMenuBlock = 0;
		
		String[][] menuTouchAreas = new String[entries.length][5]; // {entry, x0, x1, y0, y1}
		
		Rect bounds = new Rect();
		
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setTextSize(g.getPixels(25));
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.create("Roboto",Typeface.BOLD));
		
		for(String entry : entries) {
			if(entry.equalsIgnoreCase(((GravityGame)game).getResources().getString(R.string.continue_game)) && !Settings.continueGame) {
				continue;
			}
		    paint.getTextBounds(entry, 0, entry.length(), bounds);
		    widthMenuBlock = widthMenuBlock < bounds.width() ? bounds.width() : widthMenuBlock;
		    heightMenuBlock += (bounds.height() * 2);
		}
		
		int menuX = g.getWidth() / 10;
		int menuY = ((g.getHeight() - heightMenuBlock)/2) + bounds.height();
		
		int  i = 0;
		for(String entry : entries) {
			if(entry.equalsIgnoreCase(((GravityGame)game).getResources().getString(R.string.continue_game)) && !Settings.continueGame) {
				continue;
			}
			
			if(entry.equals(touchedMenuEntry)) {
				paint.setColor(Color.DKGRAY);
				paint.setTypeface(Typeface.create("Roboto",Typeface.BOLD_ITALIC));
			}else{
				paint.setColor(Color.WHITE);
				paint.setTypeface(Typeface.create("Roboto",Typeface.BOLD));
			}
			
			menuTouchAreas[i][0] = entry; 							// entry name
			menuTouchAreas[i][1] = menuX + ""; 						// x0
			menuTouchAreas[i][2] = menuX + widthMenuBlock + ""; 	// x1
			menuTouchAreas[i][3] = menuY - bounds.height() + ""; 	// y0
			menuTouchAreas[i][4] = menuY + ""; 						// y1
			
			g.drawText(entry, menuX, menuY, paint);
			
			if(entry.equals(((GravityGame)game).getResources().getString(R.string.sound))) {
				drawExplanation(menuX, menuY, widthMenuBlock, Settings.soundEnabled, R.string.sound_on, R.string.sound_off);
			}
			
			if(entry.equals(((GravityGame)game).getResources().getString(R.string.music))) {
				drawExplanation(menuX, menuY, widthMenuBlock, Settings.musicEnabled, R.string.music_on, R.string.music_off);
			}
			
			if(entry.equals(((GravityGame)game).getResources().getString(R.string.intro))) {
				drawExplanation(menuX, menuY, widthMenuBlock, Settings.introState == IntroScreen.INTRO_DO_NOT_SHOW, R.string.intro_off, R.string.intro_on);
			}
			
			if(entry.equals(((GravityGame)game).getResources().getString(R.string.autoretry))) {
				drawExplanation(menuX, menuY, widthMenuBlock, Settings.autoretry, R.string.autoretry_on, R.string.autoretry_off);
			}

            if(entry.equals(((GravityGame)game).getResources().getString(R.string.aidline))) {
                drawExplanation(menuX, menuY, widthMenuBlock, Settings.aidline, R.string.aidline_on, R.string.aidline_off);
            }

            if(entry.equals(((GravityGame)game).getResources().getString(R.string.previous))) {
                drawExplanation(menuX, menuY, widthMenuBlock, Settings.previous, R.string.previous_on, R.string.previous_off);
            }

            if(entry.equals(((GravityGame)game).getResources().getString(R.string.vibration))) {
                drawExplanation(menuX, menuY, widthMenuBlock, Settings.vibrate, R.string.vibration_on, R.string.vibration_off);
            }
			
			menuY += (bounds.height() * 2);
			i++;
		}
		
		return menuTouchAreas;
	}
	
	protected void drawInfo(String info, int x, int y) {
		Graphics g = game.getGraphics();
		
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setTextSize(g.getPixels(25));
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.create("Roboto",Typeface.BOLD));
		
		Rect bounds = new Rect();
		paint.getTextBounds(info, 0, info.length(), bounds);
		
		g.drawText(info, x, y - (bounds.height() * 2), paint);
	}
	
//	private void drawSoundExplanation(int x, int y, int blockWidth) {
//		String text;
//		if(Settings.soundEnabled) {
//			text = ((GravityGame)game).getResources().getString(R.string.sound_on);
//		} else {
//			text = ((GravityGame)game).getResources().getString(R.string.sound_off);
//		}
//		game.getGraphics().drawText(x + blockWidth + (game.getGraphics().getWidth() / 10), y, 25, text, Typeface.create("Roboto", Typeface.ITALIC));
//	}
	
//	private void drawMusicExplanation(int x, int y, int blockWidth) {
//		String text;
//		if(Settings.musicEnabled) {
//			text = ((GravityGame)game).getResources().getString(R.string.music_on);
//		} else {
//			text = ((GravityGame)game).getResources().getString(R.string.music_off);
//		}
//		game.getGraphics().drawText(x + blockWidth + (game.getGraphics().getWidth() / 10), y, 25, text, Typeface.create("Roboto",Typeface.ITALIC));
//	}
	
//	private void drawIntroExplanation(int x, int y, int blockWidth) {
//		String text;
//		if(Settings.introState == IntroScreen.INTRO_DO_NOT_SHOW) {
//			text = ((GravityGame)game).getResources().getString(R.string.intro_off);
//		} else {
//			text = ((GravityGame)game).getResources().getString(R.string.intro_on);
//		}
//		game.getGraphics().drawText(x + blockWidth + (game.getGraphics().getWidth() / 10), y, 25, text, Typeface.create("Roboto",Typeface.ITALIC));
//	}
	
//	private void drawRetryExplanation(int x, int y, int blockWidth) {
//		String text;
//		if(Settings.autoretry) {
//			text = ((GravityGame)game).getResources().getString(R.string.autoretry_on);
//		} else {
//			text = ((GravityGame)game).getResources().getString(R.string.autoretry_off);
//		}
//		game.getGraphics().drawText(x + blockWidth + (game.getGraphics().getWidth() / 10), y, 25, text, Typeface.create("Roboto",Typeface.ITALIC));
//	}

//    private void drawAidLineExplanation(int x, int y, int blockWidth) {
//        String text;
//        if(Settings.aidline) {
//            text = ((GravityGame)game).getResources().getString(R.string.aidline_on);
//        } else {
//            text = ((GravityGame)game).getResources().getString(R.string.aidline_off);
//        }
//        game.getGraphics().drawText(x + blockWidth + (game.getGraphics().getWidth() / 10), y, 25, text, Typeface.create("Roboto",Typeface.ITALIC));
//    }

//    private void drawPreviousExplanation(int x, int y, int blockWidth) {
//        String text;
//        if(Settings.previous) {
//            text = ((GravityGame)game).getResources().getString(R.string.previous_on);
//        } else {
//            text = ((GravityGame)game).getResources().getString(R.string.previous_off);
//        }
//        game.getGraphics().drawText(x + blockWidth + (game.getGraphics().getWidth() / 10), y, 25, text, Typeface.create("Roboto",Typeface.ITALIC));
//    }

//    private void drawVibrationExplanation(int x, int y, int blockWidth) {
//        String text;
//        if(Settings.vibrate) {
//            text = ((GravityGame)game).getResources().getString(R.string.vibration_on);
//        } else {
//            text = ((GravityGame)game).getResources().getString(R.string.vibration_off);
//        }
//        game.getGraphics().drawText(x + blockWidth + (game.getGraphics().getWidth() / 10), y, 25, text, Typeface.create("Roboto",Typeface.ITALIC));
//    }

    private void drawExplanation(int x, int y, int blockWidth, boolean on, int resOn, int resOff) {
        String text;
        if(on) {
            text = ((GravityGame)game).getResources().getString(resOn);
        } else {
            text = ((GravityGame)game).getResources().getString(resOff);
        }
        game.getGraphics().drawText(x + blockWidth + (game.getGraphics().getWidth() / 10), y, 25, text, Typeface.create("Roboto",Typeface.ITALIC));
    }
	
	protected boolean inBounds(TouchEvent event, String[][] touchAreas, int i) throws Exception{
		return inBounds(event, 
				Integer.parseInt(touchAreas[i][1]), 
				Integer.parseInt(touchAreas[i][2]), 
				Integer.parseInt(touchAreas[i][3]), 
				Integer.parseInt(touchAreas[i][4]));
	}
	
	protected boolean inBounds(TouchEvent event, int x0, int x1, int y0, int y1) {
        return event.x > x0 && event.x < x1 && event.y > y0 && event.y < y1;
	}

	@Override
	public void update(float deltaTime) {}

	@Override
	public void present(float deltaTime) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}
}