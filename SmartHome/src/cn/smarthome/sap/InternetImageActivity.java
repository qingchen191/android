package cn.smarthome.sap;

import java.io.InputStream;
import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.smarthome.sap.util.HttpUtils;

public class InternetImageActivity extends Activity {

    private Button btnInternet;
    private ImageView ivInternet;
    private TextView tvMsgType;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_image);

        btnInternet = (Button) findViewById(R.id.btnInternet);
        ivInternet = (ImageView) findViewById(R.id.ivInternet);
        tvMsgType = (TextView) findViewById(R.id.tbMsgType);

        // 定义一个handler，用于接收消息
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bitmap bmp = null;
                // 通过消息码确定使用什么方式传递图片信息
                if (msg.what == 0) {
                    bmp = (Bitmap) msg.obj;
                    tvMsgType.setText("使用obj传递数据");
                } else {
                    Bundle ble = msg.getData();
                    bmp = (Bitmap) ble.get("bmp");
                    tvMsgType.setText("使用Bundle传递数据");
                }
                // 设置图片到ImageView中
                ivInternet.setImageBitmap(bmp);
            }
        };

        btnInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空之前获取的数据
                tvMsgType.setText("");
                ivInternet.setImageBitmap(null);
                //定义一个线程类
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            //获取网络图片
                            InputStream inputStream = HttpUtils
                                    .getImageViewInputStream();
                            Bitmap bitmap = BitmapFactory
                                    .decodeStream(inputStream);
                            Message msg = new Message();
                            Random rd = new Random();
                            int ird = rd.nextInt(10);
                            //通过一个随机数，随机选择通过什么方式传递图片信息到消息中
                            if (ird / 2 == 0) {
                                msg.what = 0;
                                msg.obj = bitmap;
                            } else {
                                Bundle bun = new Bundle();
                                bun.putParcelable("bmp", bitmap);
                                msg.what = 1;
                                msg.setData(bun);
                            }
                            //发送消息
                            handler.sendMessage(msg);
                        } catch (Exception e) {

                        }
                    }
                }.start();
            }
        });
    }

}
