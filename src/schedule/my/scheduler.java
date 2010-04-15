package schedule.my;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import schedule.my.serverCom;;

public class scheduler extends Activity implements OnGestureListener 
{
	FrameLayout Main;
	public AlertDialog start;
	public Button tr1;
	public Button tr2;
	public Button tr3;
	public Button tr4;
	public Button tr5;
	public Button tr6;
	public Button backTracker;
	
	public serverCom com;
	
	public Button mon1;
	public Button tue1;
	public Button wed1;
	public Button back1;
	public Button sess1[];
	
	public String track_name;
	public String day_name;
	
	public TextView des;
	public TextView pre;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        tr1 = new Button(this);
        tr2 = new Button(this);
        tr3 = new Button(this);
        tr4 = new Button(this);
        tr5 = new Button(this);
        tr6 = new Button(this);
        
        backTracker = new Button(this);
        
        mon1 = new Button(this);
        tue1 = new Button(this);
        wed1 = new Button(this);
        back1 = new Button(this);
              
        sess1 = new Button[0];
        
        des = new TextView(this);
        pre = new TextView(this);
        com = new serverCom();
        
        Main = (FrameLayout) findViewById(R.id.Dlog);
		
		LayoutInflater li = LayoutInflater.from(this); 
		View dilog = li.inflate(R.layout.dialog, Main);
		
		setContentView(R.layout.main);
		
		tr1 = (Button) findViewById(R.id.tr1);
		tr2 = (Button) findViewById(R.id.tr2);
		tr3 = (Button) findViewById(R.id.tr3);
		tr4 = (Button) findViewById(R.id.tr4);
		tr5 = (Button) findViewById(R.id.tr5);
		tr6 = (Button) findViewById(R.id.tr6);
		
		backTracker = (Button)findViewById(R.id.Backtracks);
		
		des = (TextView) findViewById(R.id.DescriptionTank);
		pre = (TextView) findViewById(R.id.PresentersPerfect);
		
		mon1 = (Button)findViewById(R.id.t1B1);
		tue1 = (Button)findViewById(R.id.t1B2);
		wed1 = (Button)findViewById(R.id.t1B3);
		back1 = (Button)findViewById(R.id.backButton1);
		
		tr1.setOnClickListener(pickt);
		tr2.setOnClickListener(pickt);
		tr3.setOnClickListener(pickt);
		tr4.setOnClickListener(pickt);
		tr5.setOnClickListener(pickt);
		tr6.setOnClickListener(pickt);
		
		backTracker.setOnClickListener(pickt);
		
		mon1.setOnClickListener(clickt);
		tue1.setOnClickListener(clickt);
		wed1.setOnClickListener(clickt);
		back1.setOnClickListener(clickt);
		
	    AlertDialog.Builder startCrt  = new AlertDialog.Builder(scheduler.this);
	    startCrt.setTitle("Welcome!");
	    startCrt.setPositiveButton("View sessions", goodButton); 
		startCrt.setNegativeButton("Exit", badButton);
		startCrt.setCancelable(true);
		startCrt.setView(dilog);
		
		start = startCrt.create();
		start.show();
    }
    
    OnClickListener pickt = new OnClickListener()
	{
	
		public void onClick(View v) 
		{
			int vid = v.getId();
			ViewFlipper vf = new ViewFlipper(scheduler.this);
			vf = (ViewFlipper) findViewById(R.id.Flippcon);
			switch(vid)
			{
				case R.id.tr1:{ track_name = "Welcome to Drupal";vf.showNext(); break;}
				case R.id.tr2:{ track_name = "Design, Theme, and Usability";vf.showNext(); break;}
				case R.id.tr3:{ track_name = "Under the Hood";vf.showNext(); break;}
				case R.id.tr4:{ track_name = "Configuration, Set-up &amp; Administration";vf.showNext(); break;}
				case R.id.tr5:{ track_name = "Providing Professional Drupal Services";vf.showNext(); break;}
				case R.id.tr6:{ track_name = "Leveraging Drupal for your business";vf.showNext(); break;}
				case R.id.Backtracks:{vf.showPrevious(); break;}
			}
		}
	};
	
	OnClickListener clickt = new OnClickListener()
	{
	
		public void onClick(View v) 
		{
			int vid = v.getId();
			switch(vid)
			{
				case R.id.t1B1:
				{
					day_name = "Monday";
					String schtable = com.getAns(getResources().getString(R.string.json_url),"method", "presspectiva.drupalcon_by_day", "track", track_name, "day", "Monday", "flag", "0", "", ""); 
					String res[] = splitter(schtable,"\n");
					int textopMargin = -15;
					int arrlen = res.length;
					FrameLayout.LayoutParams texparpar[] = new FrameLayout.LayoutParams[arrlen];
					FrameLayout con = (FrameLayout) findViewById(R.id.sessions1);
					sess1 = new Button[arrlen];
					for(int i=0;i<arrlen;i++)
					{
						texparpar[i] = new FrameLayout.LayoutParams(320, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP);
						sess1[i] = new Button(scheduler.this);
						textopMargin +=  15+v.getHeight();
						texparpar[i].topMargin = textopMargin;
						sess1[i].setMaxLines(3);
						sess1[i].setMinLines(3);
						sess1[i].setText(res[i]);
						sess1[i].setId(i);
						sess1[i].setOnClickListener(flickt);
						con.addView(sess1[i], texparpar[i]);
					}
					ViewFlipper vfs1 = new ViewFlipper(scheduler.this);
					vfs1 = (ViewFlipper) findViewById(R.id.track1Flipp);
					vfs1.showNext();
					break;
				}
				case R.id.t1B2:
				{
					day_name = "Tuesday";
					String schtable = com.getAns(getResources().getString(R.string.json_url),"method", "presspectiva.drupalcon_by_day", "track", track_name, "day", "Tuesday", "flag", "0", "", ""); 
					String res[] = splitter(schtable,"\n");
					//text1.setText(res[0]);
					int textopMargin = -15;
					int arrlen = res.length;
					FrameLayout.LayoutParams texparpar[] = new FrameLayout.LayoutParams[arrlen];
					FrameLayout con = (FrameLayout) findViewById(R.id.sessions1);
					sess1 = new Button[arrlen];
					for(int i=0;i<arrlen;i++)
					{
						texparpar[i] = new FrameLayout.LayoutParams(320, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP);
						sess1[i] = new Button(scheduler.this);
						textopMargin +=  15+v.getHeight();
						texparpar[i].topMargin = textopMargin;
						sess1[i].setMaxLines(3);
						sess1[i].setMinLines(3);
						sess1[i].setText(res[i]);
						sess1[i].setId(i);
						sess1[i].setOnClickListener(flickt);
						con.addView(sess1[i], texparpar[i]);
					}
					ViewFlipper vfs1 = new ViewFlipper(scheduler.this);
					vfs1 = (ViewFlipper) findViewById(R.id.track1Flipp);
					vfs1.showNext();
					break;
				}
				case R.id.t1B3:
				{
					day_name = "Wednesday";
					String schtable = com.getAns(getResources().getString(R.string.json_url),"method", "presspectiva.drupalcon_by_day", "track", track_name, "day", "Wednesday", "flag", "0", "", ""); 
					String res[] = splitter(schtable,"\n");
					//text1.setText(res[0]);
					int textopMargin = -15;
					int arrlen = res.length;
					FrameLayout.LayoutParams texparpar[] = new FrameLayout.LayoutParams[arrlen];
					FrameLayout con = (FrameLayout) findViewById(R.id.sessions1);
					sess1 = new Button[arrlen];
					for(int i=0;i<arrlen;i++)
					{
						texparpar[i] = new FrameLayout.LayoutParams(320, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP);
						sess1[i] = new Button(scheduler.this);
						textopMargin +=  15+v.getHeight();
						texparpar[i].topMargin = textopMargin;
						sess1[i].setMaxLines(3);
						sess1[i].setMinLines(3);
						sess1[i].setText(res[i]);
						sess1[i].setId(i);
						sess1[i].setOnClickListener(flickt);
						con.addView(sess1[i], texparpar[i]);
					}
					ViewFlipper vfs1 = new ViewFlipper(scheduler.this);
					vfs1 = (ViewFlipper) findViewById(R.id.track1Flipp);
					vfs1.showNext();
					break;
				}
				case R.id.backButton1:
				{
					FrameLayout con = (FrameLayout) findViewById(R.id.sessions1);
					if(sess1.length>0)
					{
						for(int i=0; i<sess1.length; i++)
						{
							con.removeView(sess1[i]);
						}
					}
					des.setText("");
					pre.setText("");
					ViewFlipper vfs1 = new ViewFlipper(scheduler.this);
					vfs1 = (ViewFlipper) findViewById(R.id.track1Flipp);
					vfs1.showPrevious();
					break;
				}
			}
		}
	};
	
	OnClickListener flickt = new OnClickListener()
	{
	
		public void onClick(View v) 
		{
			v.setPressed(true);
			
			int cleantopMargin = back1.getHeight()-v.getHeight();
			FrameLayout.LayoutParams cleanpar[] = new FrameLayout.LayoutParams[sess1.length];
			for(int i=0;i<sess1.length;i++)
			{
				cleanpar[i] = new FrameLayout.LayoutParams(320, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP);
				cleantopMargin +=  v.getHeight();
				cleanpar[i].topMargin = cleantopMargin;
				sess1[i].setLayoutParams(cleanpar[i]);
			}
			
			int vid = v.getId();
			String schtable = com.getAns(getResources().getString(R.string.json_url),"method", "presspectiva.drupalcon_by_day", "track", track_name, "day", day_name, "flag", "1", "", ""); 
			String res[] = splitter(schtable,"\n");
			String tempString = "Description: \n " + res[2*vid+1];
			des.setText(tempString);
			tempString = "Presenters: \n " + res[2*vid];
			pre.setText(tempString);
			
			FrameLayout.LayoutParams texparpar[] = new FrameLayout.LayoutParams[sess1.length];
			int textopMargin = (back1.getHeight()+(vid*v.getHeight())+90);
			for(int i=vid+1;i<sess1.length;i++)
			{
				texparpar[i] = new FrameLayout.LayoutParams(320, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP);
				textopMargin +=  v.getHeight();
				texparpar[i].topMargin = textopMargin;
				sess1[i].setLayoutParams(texparpar[i]);
			}
			
			ScrollView texts = new ScrollView(scheduler.this);
			texts = (ScrollView)findViewById(R.id.Textscrollcon);
			FrameLayout.LayoutParams texpar = new FrameLayout.LayoutParams(320, 90, Gravity.TOP);
			texpar.topMargin = (back1.getHeight()+(vid*v.getHeight())+60);
			texts.setLayoutParams(texpar);
		}
	};
    
	public String[] splitter(String in, String delim)
	{
		String[] ans;
		ans = in.split(delim);
		return ans;
	}//splitter
	
    DialogInterface.OnClickListener goodButton = new DialogInterface.OnClickListener() //initialize click listener for raise
	{
		public void onClick(DialogInterface dialog, int id) 
        {
			Toast.makeText(scheduler.this, "Please pick your desired track.", Toast.LENGTH_SHORT).show(); 
			//String schtable = com.getAns(getResources().getString(R.string.json_url),"method", "presspectiva.drupalcon_by_day", "track", "welcome-to-drupal", "day", "Monday", "", "", "", "");
			//text.setText(schtable);
        }
    };
    
    DialogInterface.OnClickListener badButton = new DialogInterface.OnClickListener()  //initialize click listener for raise
	{
			public void onClick(DialogInterface dialog, int id) 
		    {
				Toast.makeText(scheduler.this, "But, you just got here!", Toast.LENGTH_SHORT).show(); 
		        scheduler.this.finish();
		    }
    };
    
	public boolean onDown(MotionEvent e){return false;}
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY){return false;}
	public void onLongPress(MotionEvent e){}
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,float distanceY){return false;}
	public void onShowPress(MotionEvent e){}
	public boolean onSingleTapUp(MotionEvent e){return false;}
	
	   // Deal with the option menu 
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.options_menu, menu);
	    return true;
	}
	
	// Handles item selections
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.quit:
	    	Toast.makeText(scheduler.this, "Had enough, did you?", Toast.LENGTH_SHORT).show();
	    	scheduler.this.finish();
	        // Not Reached 
	    }
	    return false;
	}
}