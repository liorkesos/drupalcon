package schedule.my;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class serverCom 
{
	public static DefaultHttpClient http_client;
	public static HttpPost poster;
	public static String ans;
	
	
	
	public serverCom()
	{
		http_client = new DefaultHttpClient();
		poster = new HttpPost();
    	
	}//Default Ctor 
	
	
	public String getVar(String Url, String name,String val,String arg1,String aval1, String arg2,
			String aval2, String arg3, String aval3, String arg4, String aval4)
    {
		poster = new HttpPost(Url);
         try {
	        	HttpParams params = new BasicHttpParams();
	   			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	 			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
	 			 
	 			params.setParameter(name, val );
	 			params.setParameter(arg1, aval1);
	 			params.setParameter(arg2, aval2);
	 			params.setParameter(arg3, aval3);
	 			params.setParameter(arg4, aval4);
	            
	 			http_client.setParams(params);
	             
	 			UrlEncodedFormEntity reqEnt = initFormEnt
	 			   (name, val, arg1, aval1, arg2, aval2, arg3, aval3, arg4, aval4);	//create request entity
	             poster.setEntity(reqEnt);											//post request entity
	             
	             //HttpResponse response = null;
	             //HttpEntity ent = null;
	             
	             //try
	             //{
	            	 HttpResponse response = http_client.execute(poster);  
	            	 HttpEntity ent = response.getEntity();
	             //}catch(IllegalStateException e){e.printStackTrace();}
	              //catch(NullPointerException ne){ne.printStackTrace();}
	              
	             
                 if(ent!=null)
                 {
                	  InputStream is = ent.getContent();
                      BufferedInputStream bis = new BufferedInputStream(is);
                      ans = convertStreamToString(bis);
                     
                      JSONObject json = new JSONObject(ans);
                     
      				boolean server_error = json.getBoolean("#error");
      				if(server_error) 
      				{
      					throw (new ClientProtocolException(json.getString("#data")));
      				}
      				
                    is.close();
                    bis.close();
                    return ans;
                   } 
                 else
               	  return null;
         		} catch (IOException e){e.printStackTrace();} 
                  catch (JSONException e) {e.printStackTrace();}
                  
                 return ans;
       }
	
	public String getAns(String Url, String name,String val,String arg1,String aval1, String arg2,
			String aval2, String arg3, String aval3, String arg4, String aval4)
    {
		poster = new HttpPost(Url);
		try {
		 	 HttpParams params = new BasicHttpParams();
   			 HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
 			 HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
 			 params.setParameter(name, val );
 			 params.setParameter(arg1, aval1);
 			 params.setParameter(arg2, aval2);
 			 params.setParameter(arg3, aval3);
 			 params.setParameter(arg4, aval4);
            
 			 http_client.setParams(params);
 			 try{
 				UrlEncodedFormEntity reqEnt = initFormEnt
  			   (name, val, arg1, aval1, arg2, aval2, arg3, aval3, arg4, aval4);	//create request entity
              poster.setEntity(reqEnt);											//post request entity
              
              HttpResponse response = http_client.execute(poster);  
              HttpEntity ent = response.getEntity();
              InputStream is = ent.getContent();
              BufferedInputStream bis = new BufferedInputStream(is);
              ans = convertStreamToString(bis);
             
              JSONObject json = new JSONObject(ans);
             
              boolean server_error = json.getBoolean("#error");
 			 if(server_error) 
 			 {
 			 	throw (new ClientProtocolException(json.getString("#data")));
 			 }
              is.close();
              bis.close();
              return(json.getString("#data"));
			 }catch (IOException e){e.printStackTrace();} 
			  catch (JSONException e) {e.printStackTrace();}
			  return null;
     		} catch(IllegalStateException e){e.printStackTrace();}
              catch(NullPointerException ne){ne.printStackTrace();}
             return "3";
       }
    
    private static UrlEncodedFormEntity initFormEnt
    (String name,String val,String arg1,String aval1, String arg2, String aval2, String arg3, String aval3, String arg4, String aval4) 
    	throws UnsupportedEncodingException 
    {
	    ArrayList<NameValuePair> values = new ArrayList<NameValuePair>(6);
	    values.add(new BasicNameValuePair(name,val));
	    values.add(new BasicNameValuePair(arg1, aval1));
	    values.add(new BasicNameValuePair(arg2, aval2));
	    values.add(new BasicNameValuePair(arg3, aval3));
	    values.add(new BasicNameValuePair(arg4, aval4));
	    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values);
	    return entity;
	}
    
    private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
