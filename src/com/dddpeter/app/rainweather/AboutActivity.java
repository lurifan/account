package com.dddpeter.app.rainweather;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class AboutActivity extends FinalActivity {
	@ViewInject(id = R.id.info) TextView textAbout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		String html="<center><h1>֪������</h1></center>" +
				"<p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; �����Ϊ������Ʒ����Ҫ������ͨ��GPS����A-GPS��λ�������ȡ������Ϣ��" +
				"�Լ����ֳ��е�PM2.5��Ϣ(��Щ������δ����PM2.5)�����˽���ʹ�ã���������ҵ��;��" +
				"<p><h5>���ߣ�����֮��</h5>" +
				"<p><h5>�����ʼ���dddpeter@126.com</h5>";
		textAbout.setText(Html.fromHtml(html));
	}

}
