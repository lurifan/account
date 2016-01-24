package com.dddpeter.app.rainweather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dddpeter.app.rainweather.object.ParamApplication;

public class RecentActivity extends  FinalActivity{
	@ViewInject(id = R.id.recent)
	RelativeLayout my;
	final int REFRESH_MSG=0x1000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 this.setContentView(R.layout.activity_recent);
		 
		 final TextView text =new TextView(this);
		 final  Handler refreshHandler=new Handler(){
				public void handleMessage(Message msg){
					if(msg.what==REFRESH_MSG){
						try {
							 ParamApplication application=(ParamApplication) RecentActivity.this.getApplicationContext();
					    	 
							updateContent( application.getWeatherObjectDetail());
						} catch (Exception e) {
						
							e.printStackTrace();
						}
					}
				}
			};
			new Runnable(){
				@Override
				public void run() {
						
						     try {
						    	
									Message msg=new Message();
									msg.what=REFRESH_MSG;
									refreshHandler.sendMessage(msg);
									
								} catch (Exception e) {
									e.printStackTrace();
									Toast.makeText(RecentActivity.this, "��δ���������Ϣ���붨λ��ˢ��������Ϣ", Toast.LENGTH_SHORT).show();
									text.setText(Html.fromHtml("<h1>��δ���������Ϣ���붨λ��ˢ��������Ϣ</h1>"));
								}
						}
						
							
					
				}.run();
		
	}
	protected void updateContent(JSONObject weatherObjectDetail) throws JSONException, ParseException {
		JSONObject temp0=weatherObjectDetail.getJSONObject("weatherinfo");
		String todayStr=temp0.getString("date_y");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy��MM��dd��",Locale.CHINESE);
		SimpleDateFormat sdf1=new SimpleDateFormat("MM��dd��",Locale.CHINESE);	
		String[] weekDays = {"������", "����һ", "���ڶ�", "������", "������", "������", "������"};
		Date today=sdf.parse(todayStr.trim());
		String[] tempratures=new String[5];
		String[] weathers=new String[5];
		String[] days=new String[5];
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		// 1, ������ʾ����Ⱦͼ
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        // 2,������ʾ
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // 2.1, ��������
            XYSeries seriesHigh = new XYSeries("����¶�");
            XYSeries seriesLow = new XYSeries("����¶�");
		for(int i=0;i<5;i++){
			tempratures[i]=temp0.getString("temp"+(i+2));
			String[] temps=tempratures[i].replace("��", "").split("~");
			weathers[i]=temp0.getString("weather"+(i+2));
			cal.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH)+1);
			days[i]=sdf1.format(cal.getTime());
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
	        if (w < 0) {w = 0;  }
			 renderer.addXTextLabel(i+1, days[i]+"\n"+weekDays[w]+"\n"+weathers[i]);
			 if(Integer.parseInt(temps[1].trim())>Integer.parseInt(temps[0].trim())){
				 seriesHigh.add(i+1, Integer.parseInt(temps[1].trim()));
				 seriesLow.add(i+1, Integer.parseInt(temps[0].trim()));
			 }
			 else{
				 seriesHigh.add(i+1, Integer.parseInt(temps[0].trim()));
				 seriesLow.add(i+1, Integer.parseInt(temps[1].trim()));
			 }
			 
			 //System.out.println(Integer.parseInt(temps[0].trim()));
		}
        renderer.setAxesColor(this.getResources().getColor(R.color.myblue));
        renderer.setZoomEnabled(false,false);
        renderer.setPanEnabled(false,false);
        renderer.setXLabels(0); 
        renderer.setExternalZoomEnabled(true);
        renderer.setAxisTitleTextSize(20);
        renderer.setLegendTextSize(20);
        renderer.setChartTitleTextSize(30);
        renderer.setMarginsColor(this.getResources().getColor(R.color.myblue));
        renderer.setYTitle("�¶�(��)");
        renderer.setApplyBackgroundColor(true);
        renderer.setFitLegend(true);
        renderer.setLabelsTextSize(20);
        renderer.setMargins(new int[] { 50, 50, 70, 50 });//����ͼ�����߿�(��/��/��/��)
        renderer.setZoomRate(1.1f);
        renderer.setPointSize(10);
        renderer.setBackgroundColor(this.getResources().getColor(R.color.skyblue));
        renderer.setChartTitle("δ��5������");
        renderer.setShowGrid( true ); 
            dataset.addSeries(seriesLow);
            dataset.addSeries(seriesHigh);
        // 3, �Ե�Ļ��ƽ�������
        XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
        // 3.1������ɫ
       
        xyRenderer.setColor(Color.GREEN);
        xyRenderer.setDisplayChartValues(true);
        xyRenderer.setFillPoints(true);
        xyRenderer.setLineWidth(10);
        xyRenderer.setChartValuesTextAlign(Align.CENTER);
        xyRenderer.setChartValuesTextSize(20);
        xyRenderer.setChartValuesSpacing(30);
        // 3.2���õ����ʽ
        xyRenderer.setPointStyle(PointStyle.CIRCLE);
        // 3.3, ��Ҫ���Ƶĵ���ӵ����������
        renderer.addSeriesRenderer(xyRenderer);
        // 3.4,�ظ� 1~3�Ĳ�����Ƶڶ���ϵ�е�
        xyRenderer = new XYSeriesRenderer();
        xyRenderer.setColor(Color.RED);
        xyRenderer.setDisplayChartValues(true);
        xyRenderer.setFillPoints(true);
        xyRenderer.setLineWidth(10);
        xyRenderer.setChartValuesTextAlign(Align.CENTER);
        xyRenderer.setChartValuesTextSize(20);
        xyRenderer.setChartValuesSpacing(30);
        xyRenderer.setPointStyle(PointStyle.CIRCLE);
        renderer.addSeriesRenderer(xyRenderer);
        View view = ChartFactory.getLineChartView(this, dataset, renderer);  
        view.setBackgroundColor(this.getResources().getColor(R.color.myblue));
      
       @SuppressWarnings("deprecation")
	RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
    		   RelativeLayout.LayoutParams.FILL_PARENT,
    		   RelativeLayout.LayoutParams.FILL_PARENT
		);
       view.setLayoutParams(params1);
       my.addView(view,params1);
		
	}

}
