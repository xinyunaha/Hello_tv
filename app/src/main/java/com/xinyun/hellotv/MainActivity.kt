package com.xinyun.hellotv

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.graphics.Color.parseColor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log.d
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.devbrackets.android.exomedia.ui.widget.VideoView
import kotlinx.android.synthetic.main.videos_player.*
import org.json.JSONArray
import org.json.JSONObject


@Suppress("NAME_SHADOWING")
class MainActivity : Activity() {
    private var Tv_number_max = 0
    private var Source_number_max = 0
    private var Tv_number = 0
    private var Source_address_number = 0
    private lateinit var Source: String
    private lateinit var Source_obj:JSONObject
    private lateinit var Source_data: JSONArray
    private lateinit var Source_name: String
    private lateinit var Source_adds: JSONArray
    private lateinit var Source_add:String
    private var initialX: Float = 0F
    private var initialY: Float = 0F
    private var move = 200
    private var mHandler: Handler = Handler()
    private fun get_info(){
        Source_name= Source_data.getJSONObject(Tv_number).getString("name")
        Source_adds = Source_data.getJSONObject(Tv_number).getJSONArray("source")
        Source_add = Source_adds.getString(Source_address_number)
    }
    private fun showinfo(){
        var text_tv_number = findViewById<TextView>(R.id.tv_number)
        var text_tv_info = findViewById<TextView>(R.id.tv_info)
        if(Tv_number + 1 < 10){
            text_tv_number.setText("00"+"${Tv_number + 1}")
        }
        else if(Tv_number + 1 < 99){
            text_tv_number.setText("0"+"${Tv_number + 1}")
        }else{
            text_tv_number.setText("${Tv_number + 1}")
        }
        d(TAG, "${Source_name + 1}\n节目源:${Source_address_number + 1}/${Source_number_max + 1}\n节目数:${Tv_number + 1}/${Tv_number_max + 1}")
        text_tv_info.setText("${Source_name}\n节目源:${Source_address_number + 1}/${Source_number_max + 1}\n节目数:${Tv_number + 1}/${Tv_number_max + 1}")
    }
    private fun closes_info(){
        var mTime = 3
        val mRunnable: Runnable = object : Runnable {
            override fun run() {
                if (mTime == 0) {
                    info_background.setBackgroundColor(parseColor("#007a7374"))
                    tv_info.text = ""
                    tv_number.text = ""
                }
                mTime--
                mHandler.postDelayed(this, 1_000L)
            }
        }
        mHandler.removeCallbacks(mRunnable)
        mHandler.postDelayed(mRunnable, 1_000L)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val Start_status = findViewById<TextView>(R.id.start_status)
        val network_test = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, "https://www.baidu.com",
            Response.Listener<String> { response ->
                Start_status.text = "节目正在努力加载中  (๑•̀ㅂ•́)و✧"
                //读取json
                Source = "{\"data\":[{\"name\":\"CCTV-1\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000210103.m3u8\",\"http://hwzbout.yunshicloud.com/0spq8i/r04v8s.m3u8\",\"http://by4.nty.tv189.cn/tm-cctv1tm-800k.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv1_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv1_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/live/cctv1_2/index.m3u8\",\"http://live5.plus.hebtv.com/cctv1/sd/live.m3u8\",\"http://221.6.85.150:9000/live/cctv1_800/cctv1_800.m3u8\",\"http://183.207.249.9/PLTV/3/224/3221225530/index.m3u8\",\"http://sdlqx.chinashadt.com:2036/live/CCTV1.stream/playlist.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225710/index.m3u8\",\"http://39.134.52.169/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225977/index.m3u8\",\"http://117.169.120.145:8080/live/cctv-1/.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/live/cctv1_2/index.m3u8\",\"http://223.110.245.159/ott.js.chinamobile.com/PLTV/3/224/3221227462/index.m3u8\",\"http://hbzq.chinashadt.com:1936/live/zaoqiang2.stream_360p/palylist.m3u8\",\"http://117.156.28.60/PLTV/88888888/224/3221225691/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226154/index.m3u8\"]},{\"name\":\"CCTV-2\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000203603.m3u8\",\"http://by4.nty.tv189.cn/tm-cctv2tm-800k.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv2_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv2_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/live/cctv2_2/index.m3u8\",\"http://221.6.85.150:9000/live/cctv2_800/cctv2_800.m3u8\",\"http://39.134.52.159/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225974/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/live/cctv2_2/index.m3u8\",\"http://117.169.120.145:8080/live/cctv-2/.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225588/index.m3u8\",\"http://223.110.245.170/ott.js.chinamobile.com/PLTV/3/224/3221227207/index.m3u8\",\"http://117.156.28.54/PLTV/88888888/224/3221225658/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226138/index.m3u8\"]},{\"name\":\"CCTV-3\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000203803.m3u8\",\"http://by4.nty.tv189.cn/tm-cctv3tm-800k.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv3_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv3_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/live/cctv3_2/index.m3u8\",\"http://221.6.85.150:9000/live/cctv3_800/cctv3_800.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225606/index.m3u8\",\"http://sdlqx.chinashadt.com:2036/live/CCTV3.stream/playlist.m3u8\",\"http://117.169.120.145:8080/live/cctv-3/.m3u8\",\"http://183.207.249.9/PLTV/3/224/3221225588/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/live/cctv3_2/index.m3u8\"]},{\"name\":\"CCTV-4\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000204803.m3u8\",\"http://by4.nty.tv189.cn/tm-cctv4tm-800k.m3u8\",\"http://live6.plus.hebtv.com/cctv4/sd/live.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv4_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv4_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/live/cctv4_2/index.m3u8\",\"http://221.6.85.150:9000/live/cctv4_800/cctv4_800.m3u8\",\"http://39.134.52.161/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225978/index.m3u8\",\"http://39.134.52.161/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225978/index.m3u8\",\"http://117.169.120.142:8080/live/cctv-4/.m3u8\",\"http://183.207.249.11/PLTV/3/224/3221225534/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/live/cctv4_2/index.m3u8\",\"http://223.110.245.163/ott.js.chinamobile.com/PLTV/3/224/3221227378/index.m3u8\",\"http://117.156.28.59/PLTV/88888888/224/3221225652/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226171/index.m3u8\"]},{\"name\":\"CCTV-6\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000203303.m3u8\",\"http://by4.nty.tv189.cn/tm-cctv6tm-800k.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv6_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv6_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/live/cctv6_2/index.m3u8\",\"http://117.156.28.53/PLTV/88888888/224/3221225634/index.m3u8\",\"http://221.6.85.150:9000/live/cctv6_800/cctv6_800.m3u8\",\"http://sdlqx.chinashadt.com:2036/live/CCTV6.stream/playlist.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225607/index.m3u8\",\"http://117.169.120.144:8080/live/cctv-6/.m3u8\",\"http://183.207.249.9/PLTV/3/224/3221225548/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/live/cctv6_2/index.m3u8\",\"http://223.110.245.159/ott.js.chinamobile.com/PLTV/3/224/3221227301/index.m3u8\"]},{\"name\":\"CCTV-7\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000510003.m3u8\",\"http://by4.nty.tv189.cn/tm-cctv7tm-800k.m3u8\",\"http://live6.plus.hebtv.com/cctv7/sd/live.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv7_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv7_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/live/cctv7_2/index.m3u8\",\"http://221.6.85.150:9000/live/cctv7_800/cctv7_800.m3u8\",\"http://183.207.249.9/PLTV/3/224/3221225546/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225733/index.m3u8\",\"http://117.169.120.141:8080/live/cctv-7/.m3u8\",\"http://39.134.52.167/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225979/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/live/cctv7_2/index.m3u8\",\"http://117.156.28.59/PLTV/88888888/224/3221225675/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226122/index.m3u8\"]},{\"name\":\"CCTV-8\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000203903.m3u8\",\"http://by4.nty.tv189.cn/tm-cctv8tm-800k.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv8_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv8_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/live/cctv8_2/index.m3u8\",\"http://221.6.85.150:9000/live/cctv8_800/cctv8_800.m3u8\",\"http://117.156.28.57/PLTV/88888888/224/3221225712/index.m3u8\",\"http://183.207.249.15/PLTV/2/224/3221226109/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225608/index.m3u8\",\"http://117.169.120.140:8080/live/cctv-8/.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/live/cctv8_2/index.m3u8\",\"http://223.110.245.167/ott.js.chinamobile.com/PLTV/3/224/3221227204/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226493/index.m3u8\"]},{\"name\":\"CCTV-9\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000499403.m3u8\",\"http://by4.nty.tv189.cn/tm-cctv9tm-800k.m3u8\",\"http://live6.plus.hebtv.com/cctv9/sd/live.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctvjilu_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctvjilu_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/live/cctvjilu_2/index.m3u8\",\"http://221.6.85.150:9000/live/cctv9_800/cctv9_800.m3u8\",\"http://183.207.249.13/PLTV/3/224/3221225532/index.m3u8\",\"http://117.169.120.145:8080/live/cctv-news/.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225734/index.m3u8\",\"http://223.110.245.161/ott.js.chinamobile.com/PLTV/3/224/3221225868/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/live/cctvjilu_2/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226156/index.m3u8\"]},{\"name\":\"CCTV-10\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000203503.m3u8\",\"http://by4.nty.tv189.cn/tm-cctv10tm-800k.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv10_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv10_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/live/cctv10_2/index.m3u8\",\"http://live6.plus.hebtv.com/cctv10/sd/live.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225730/index.m3u8\",\"http://221.6.85.150:9000/live/cctv10_800/cctv10_800.m3u8\",\"http://183.207.249.7/PLTV/3/224/3221225550/index.m3u8\",\"http://117.169.120.140:8080/live/cctv-10/.m3u8\",\"http://39.134.52.184/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225984/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/live/cctv10_2/index.m3u8\",\"http://223.110.245.170/ott.js.chinamobile.com/PLTV/3/224/3221225550/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226199/index.m3u8\"]},{\"name\":\"CCTV-11\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000204103.m3u8\",\"http://by4.nty.tv189.cn/tm-cctv11tm-800k.m3u8\",\"http://live6.plus.hebtv.com/cctv11/sd/live.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv11_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv11_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/live/cctv11_2/index.m3u8\",\"http://221.6.85.150:9000/live/cctv11_800/cctv11_800.m3u8\",\"http://117.169.120.143:8080/live/cctv-11/.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/live/cctv11_2/index.m3u8\",\"http://223.110.245.153/ott.js.chinamobile.com/PLTV/3/224/3221227384/index.m3u8\",\"http://39.134.52.163/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225983/index.m3u8\",\"http://117.156.28.60/PLTV/88888888/224/3221225636/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226219/index.m3u8\"]},{\"name\":\"CCTV-13\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000204603.m3u8\",\"http://by4.nty.tv189.cn/tm-cctvnewstm-800k.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225555/chunklist.m3u8\",\"http://live6.plus.hebtv.com/cctv13/sd/live.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv13_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv13_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/live/cctv13_2/index.m3u8\",\"http://39.134.52.179/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225986/index.m3u8\",\"http://221.6.85.150:9000/live/cctv13_800/cctv13_800.m3u8\",\"http://183.207.249.13/PLTV/2/224/3221226021/index.m3u8\",\"http://117.169.120.140:8080/live/cctv-13/.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/live/cctv13_2/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225599/index.m3u8\",\"http://223.110.245.155/ott.js.chinamobile.com/PLTV/3/224/3221226021/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226193/index.m3u8\"]},{\"name\":\"CCTV-14\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000204403.m3u8\",\"http://by4.nty.tv189.cn/tm-cctvchildtm-800k.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctvchild_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctvchild_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/live/cctvchild_2/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225732/index.m3u8\",\"http://221.6.85.150:9000/live/cctv14_800/cctv14_800.m3u8\",\"http://183.207.249.15/PLTV/2/224/3221226023/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/live/cctvchild_2/index.m3u8\",\"http://223.110.245.170/ott.js.chinamobile.com/PLTV/3/224/3221227201/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226126/index.m3u8\"]},{\"name\":\"CCTV-15\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000205003.m3u8\",\"http://by4.nty.tv189.cn/tm-cctvmusictm-800k.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv15_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/live/cctv15_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/live/cctv15_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/live/cctv15_2/index.m3u8\",\"http://221.6.85.150:9000/live/cctv15_800/cctv15_800.m3u8\",\"http://183.207.249.7/PLTV/2/224/3221226025/index.m3u8\",\"http://117.169.120.143:8080/live/cctv-15/.m3u8\",\"http://223.110.245.143/ott.js.chinamobile.com/PLTV/3/224/3221226025/index.m3u8\",\"http://117.156.28.59/PLTV/88888888/224/3221225644/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226112/index.m3u8\"]},{\"name\":\"CETV-1\",\"source\":[\"http://cctvalih5ca.v.myalicdn.com/cstv/cetv1_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/cstv/cetv1_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/cstv/cetv1_2/index.m3u8\",\"http://117.169.120.140:8080/live/jiaoyutv/.m3u8\",\"http://39.134.52.169/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225977/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225652/index.m3u8\",\"http://117.156.28.59/PLTV/88888888/224/3221225684/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/cstv/cetv1_2/index.m3u8\"]},{\"name\":\"CETV-2\",\"source\":[\"http://cctvalih5ca.v.myalicdn.com/cstv/cetv2_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/cstv/cetv2_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/cstv/cetv2_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/cstv/cetv2_2/index.m3u8\"]},{\"name\":\"CETV-3\",\"source\":[\"http://cctvalih5ca.v.myalicdn.com/cstv/cetv3_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/cstv/cetv3_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/cstv/cetv3_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/cstv/cetv3_2/index.m3u8\"]},{\"name\":\"北京卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000272103.m3u8\",\"http://by4.nty.tv189.cn/tm-btv1hd-4000k.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/btv1_2/index.m3u8\",\"http://live1.plus.hebtv.com/bjws/sd/live.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/btv1_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/btv1_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/btv1_2/index.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225533/chunklist.m3u8\",\"http://39.134.52.163/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225988/index.m3u8\",\"http://223.110.245.163/ott.js.chinamobile.com/PLTV/3/224/3221227436/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225728/index.m3u8\",\"http://183.207.249.7/PLTV/3/224/3221225574/index.m3u8\",\"http://117.169.120.142:8080/live/hdbeijingstv/.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226164/index.m3u8\"]},{\"name\":\"东方卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000292403.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/dongfang_2/index.m3u8\",\"http://by4.nty.tv189.cn/tm-dfwshd-4000k.m3u8\",\"http://live1.plus.hebtv.com/dfws/sd/live.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/dongfang_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/dongfang_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/dongfang_2/index.m3u8\",\"http://221.6.85.150:9000/live/dfws_800/dfws_800.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225735/index.m3u8\",\"http://117.169.120.143:8080/live/hddongfangstv/.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225548/chunklist.m3u8\",\"http://117.156.28.57/PLTV/88888888/224/3221225660/index.m3u8\",\"http://223.110.245.159/ott.js.chinamobile.com/PLTV/3/224/3221227396/index.m3u8\",\"http://39.134.52.184/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225976/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226142/index.m3u8\"]},{\"name\":\"广东卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000292703.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/guangdong_2/index.m3u8\",\"http://live1.plus.hebtv.com/gdws/sd/live.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/guangdong_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/guangdong_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/guangdong_2/index.m3u8\",\"http://223.110.245.145/ott.js.chinamobile.com/PLTV/3/224/3221227249/index.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225541/chunklist.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225736/index.m3u8\",\"http://183.207.249.9/PLTV/3/224/3221225592/index.m3u8\",\"http://117.169.120.140:8080/live/hdguangdongstv/.m3u8\",\"http://nclive.grtn.cn/gdws/sd/live.m3u8\"]},{\"name\":\"江苏卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000295603.m3u8\",\"http://by4.nty.tv189.cn/tm-jswshd-4000k.m3u8\",\"http://221.6.85.150:9000/live/jsws_800/jsws_800.m3u8\",\"http://39.134.52.165/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225989/index.m3u8\",\"http://117.169.120.142:8080/live/hdjiangsustv/.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225613/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226140/index.m3u8\"]},{\"name\":\"重庆卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000297803.m3u8\",\"http://by4.nty.tv189.cn/tm-chongqingtv-4000k.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/chongqing_2/index.m3u8\",\"http://live4.plus.hebtv.com/cqws/sd/live.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/chongqing_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/chongqing_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/chongqing_2/index.m3u8\",\"http://39.134.52.159/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226005/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225618/index.m3u8\",\"http://183.207.249.11/PLTV/2/224/3221226051/index.m3u8\",\"http://117.169.120.141:8080/live/chongqingstv/.m3u8\",\"http://117.156.28.58/PLTV/88888888/224/3221225688/index.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225549/chunklist.m3u8\"]},{\"name\":\"四川卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000295003.m3u8\",\"http://by4.nty.tv189.cn/tm-sichuantv-4000k.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/sichuan_2/index.m3u8\",\"http://live4.plus.hebtv.com/scws/sd/live.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/sichuan_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/sichuan_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/sichuan_2/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225621/index.m3u8\",\"http://117.169.120.141:8080/live/sichuanstv/.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225542/chunklist.m3u8\",\"http://223.110.245.145/ott.js.chinamobile.com/PLTV/3/224/3221225814/index.m3u8\",\"http://39.134.52.183/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226000/index.m3u8\"]},{\"name\":\"山东卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000294803.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/shandong_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/shandong_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/shandong_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/shandong_2/index.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225535/chunklist.m3u8\",\"http://223.110.245.155/ott.js.chinamobile.com/PLTV/3/224/3221227258/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225737/index.m3u8\",\"http://117.169.120.144:8080/live/hdshandongstv/.m3u8\",\"http://39.134.52.157/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225992/index.m3u8\"]},{\"name\":\"天津卫视\",\"source\":[\"http://by4.nty.tv189.cn/tm-tianjinhd-4000k.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/tianjin_2/index.m3u8\",\"http://live4.plus.hebtv.com/tjws/sd/live.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/tianjin_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/tianjin_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/tianjin_2/index.m3u8\",\"http://183.207.249.15/PLTV/2/224/3221226049/index.m3u8\",\"http://223.110.245.141/ott.js.chinamobile.com/PLTV/3/224/3221227212/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225740/index.m3u8\",\"http://117.169.120.144:8080/live/hdtianjinstv/.m3u8\",\"http://39.134.52.157/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226048/index.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225539/chunklist.m3u8\"]},{\"name\":\"陕西卫视\",\"source\":[\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/shan1xi_2/index.m3u8\",\"http://by4.nty.tv189.cn/tm-shan1xitv-800k.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/shan1xi_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/shan1xi_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/shan1xi_2/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225624/index.m3u8\",\"http://183.207.249.15/PLTV/2/224/3221226091/index.m3u8\",\"http://117.169.120.143:8080/live/shanxistv/.m3u8\",\"http://223.110.245.147/ott.js.chinamobile.com/PLTV/3/224/3221226091/index.m3u8\",\"http://39.134.52.173/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226032/index.m3u8\",\"http://117.156.28.58/PLTV/88888888/224/3221225715/index.m3u8\"]},{\"name\":\"湖南卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000296203.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225610/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226162/index.m3u8\"]},{\"name\":\"湖北卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000294503.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/hubei_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/hubei_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/hubei_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/hubei_2/index.m3u8\",\"http://223.110.245.153/ott.js.chinamobile.com/PLTV/3/224/3221227211/index.m3u8\",\"http://117.169.120.142:8080/live/hdhubeistv/.m3u8\",\"http://39.134.52.163/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225993/index.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225538/chunklist.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225705/index.m3u8\"]},{\"name\":\"东南卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000292503.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/dongnan_2/index.m3u8\",\"http://by4.nty.tv189.cn/tm-dongnantv-4000k.m3u8\",\"http://live1.plus.hebtv.com/dnws/sd/live.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/dongnan_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/dongnan_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/dongnan_2/index.m3u8\",\"http://39.134.52.169/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226046/index.m3u8\",\"http://183.207.249.15/PLTV/2/224/3221226067/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225620/index.m3u8\",\"http://117.169.120.140:8080/live/dongnanstv/.m3u8\"]},{\"name\":\"云南卫视\",\"source\":[\"http://by4.nty.tv189.cn/tm-yunnantv-800k.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/yunnan_2/index.m3u8\",\"http://live4.plus.hebtv.com/ynws/sd/live.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/yunnan_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/yunnan_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/yunnan_2/index.m3u8\",\"http://183.207.249.9/PLTV/3/224/3221225591/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225636/index.m3u8\",\"http://117.169.120.141:8080/live/yunnanstv/.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225545/chunklist.m3u8\",\"http://223.110.245.157/ott.js.chinamobile.com/PLTV/3/224/3221225591/index.m3u8\",\"http://39.134.52.183/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226029/index.m3u8\",\"http://117.156.28.60/PLTV/88888888/224/3221225659/index.m3u8\"]},{\"name\":\"深圳卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000292203.m3u8\",\"http://by4.nty.tv189.cn/tm-shenzhenhd-4000k.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/shenzhen_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/shenzhen_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/shenzhen_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/shenzhen_2/index.m3u8\",\"http://39.134.52.155/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225982/index.m3u8\",\"http://223.110.245.157/ott.js.chinamobile.com/PLTV/3/224/3221225997/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225739/index.m3u8\",\"http://117.169.120.145:8080/live/hdshenzhenstv/.m3u8\"]},{\"name\":\"贵州卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000293303.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/guizhou_2/index.m3u8\",\"http://by4.nty.tv189.cn/tm-guizhoutv-800k.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/guizhou_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/guizhou_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/guizhou_2/index.m3u8\",\"http://223.110.245.149/ott.js.chinamobile.com/PLTV/3/224/3221225787/index.m3u8\",\"http://39.134.52.163/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225998/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225626/index.m3u8\",\"http://183.207.249.9/PLTV/2/224/3221226069/index.m3u8\",\"http://117.169.120.143:8080/live/guizhoustv/.m3u8\"]},{\"name\":\"湖北卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000293403.m3u8\",\"http://by4.nty.tv189.cn/tm-hebeitv-4000k.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/hebei_2/index.m3u8\",\"http://live5.plus.hebtv.com/hbwssl/hd/live.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/hebei_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/hebei_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/hebei_2/index.m3u8\",\"http://183.207.249.9/PLTV/2/224/3221226075/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225623/index.m3u8\",\"http://117.169.120.143:8080/live/hebeistv/.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225547/chunklist.m3u8\",\"http://39.134.52.184/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226013/index.m3u8\",\"http://hbzq.chinashadt.com:1936/live/zaoqiang1.stream_360p/palylist.m3u8\"]},{\"name\":\"河南卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000296103.m3u8\",\"http://by4.nty.tv189.cn/tm-henantv-4000k.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/henan_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/henan_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/henan_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/henan_2/index.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225546/chunklist.m3u8\",\"http://183.207.249.7/PLTV/2/224/3221226065/index.m3u8\",\"http://39.134.52.181/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226018/index.m3u8\",\"http://117.169.120.143:8080/live/henanstv/.m3u8\",\"http://117.156.28.53/PLTV/88888888/224/3221225681/index.m3u8\"]},{\"name\":\"浙江卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000295503.m3u8\",\"http://live4.plus.hebtv.com/zjws/sd/live.m3u8\",\"http://221.6.85.150:9000/live/zjws_800/zjws_800.m3u8\",\"http://223.110.245.163/ott.js.chinamobile.com/PLTV/3/224/3221227215/index.m3u8\",\"http://117.169.120.141:8080/live/hdzhejiangstv/.m3u8\",\"http://183.207.249.9/PLTV/3/224/3221225544/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225612/index.m3u8\",\"http://39.134.52.179/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225975/index.m3u8\",\"http://117.148.187.37/PLTV/88888888/224/3221226136/index.m3u8\"]},{\"name\":\"安徽卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000298003.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/anhui_2/index.m3u8\",\"http://live1.plus.hebtv.com/ahws/sd/live.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/anhui_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/anhui_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/anhui_2/index.m3u8\",\"http://by4.nty.tv189.cn/tm-ahwshd-4000k.m3u8\",\"http://223.110.245.147/ott.js.chinamobile.com/PLTV/3/224/3221225634/index.m3u8\",\"http://39.134.52.157/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226045/index.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225534/chunklist.m3u8\",\"http://183.207.249.9/PLTV/3/224/3221225634/index.m3u8\",\"http://117.169.120.140:8080/live/anhuistv/.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225727/index.m3u8\"]},{\"name\":\"黑龙江卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000293903.m3u8\",\"http://by4.nty.tv189.cn/tm-heilongjianghd-4000k.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/heilongjiang_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/heilongjiang_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/heilongjiang_2/index.m3u8\",\"http://223.110.245.163/ott.js.chinamobile.com/PLTV/3/224/3221227252/index.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225544/chunklist.m3u8\",\"http://117.169.120.140:8080/live/hdheilongjiangstv/.m3u8\",\"http://39.134.52.183/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221225980/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225640/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/heilongjiang_2/index.m3u8\"]},{\"name\":\"江西卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000294103.m3u8\",\"http://by4.nty.tv189.cn/tm-jiangxitv-4000k.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/jiangxi_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/jiangxi_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/jiangxi_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/jiangxi_2/index.m3u8\",\"http://223.110.245.170/ott.js.chinamobile.com/PLTV/3/224/3221225536/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225615/index.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225537/chunklist.m3u8\",\"http://183.207.249.11/PLTV/3/224/3221225536/index.m3u8\",\"http://117.169.120.144:8080/live/jiangxistv/.m3u8\",\"http://39.134.52.169/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226044/index.m3u8\"]},{\"name\":\"陕西卫视\",\"source\":[\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/shan3xi_2/index.m3u8\",\"http://by4.nty.tv189.cn/tm-shan3xitv-800k.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/shan3xi_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/shan3xi_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/shan3xi_2/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225625/index.m3u8\",\"http://183.207.249.15/PLTV/2/224/3221226079/index.m3u8\",\"http://117.169.120.140:8080/live/shanxi1stv/.m3u8\",\"http://223.110.245.163/ott.js.chinamobile.com/PLTV/3/224/3221226079/index.m3u8\",\"http://39.134.52.184/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226021/index.m3u8\",\"http://117.156.28.60/PLTV/88888888/224/3221225719/index.m3u8\"]},{\"name\":\"辽宁卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000281303.m3u8\",\"http://by4.nty.tv189.cn/tm-liaoninghd-4000k.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/liaoning_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/liaoning_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/liaoning_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/liaoning_2/index.m3u8\",\"http://223.110.245.145/ott.js.chinamobile.com/PLTV/3/224/3221227410/index.m3u8\",\"http://117.169.120.145:8080/live/liaoningstv/.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225619/index.m3u8\",\"http://183.207.249.15/PLTV/2/224/3221226061/index.m3u8\",\"http://219.153.252.50/PLTV/88888888/224/3221225540/chunklist.m3u8\"]},{\"name\":\"吉林卫视\",\"source\":[\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/jilin_2/index.m3u8\",\"http://by4.nty.tv189.cn/tm-jilintv-800k.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/jilin_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/jilin_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/jilin_2/index.m3u8\",\"http://117.169.120.142:8080/live/jilinstv/.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225637/index.m3u8\",\"http://223.110.245.153/ott.js.chinamobile.com/PLTV/3/224/3221225883/index.m3u8\",\"http://39.134.52.184/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226006/index.m3u8\",\"http://117.156.28.60/PLTV/88888888/224/3221225680/index.m3u8\"]},{\"name\":\"广西卫视\",\"source\":[\"http://120.204.20.147/tlivecloud-cdn.ysp.cctv.cn/2000294203.m3u8\",\"http://by4.nty.tv189.cn/tm-guangxitv-800k.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/guangxi_2/index.m3u8\",\"http://live1.plus.hebtv.com/gxws/sd/live.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/guangxi_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/guangxi_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/guangxi_2/index.m3u8\",\"http://183.207.249.15/PLTV/2/224/3221226055/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225622/index.m3u8\",\"http://117.169.120.145:8080/live/guangxistv/.m3u8\",\"http://223.110.245.165/ott.js.chinamobile.com/PLTV/3/224/3221226055/index.m3u8\",\"http://39.134.52.165/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226002/index.m3u8\"]},{\"name\":\"青海卫视\",\"source\":[\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/qinghai_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/qinghai_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/qinghai_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/qinghai_2/index.m3u8\",\"http://by4.nty.tv189.cn/tm-qinghaitv-800k.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225628/index.m3u8\",\"http://117.169.120.145:8080/live/qinghaistv/.m3u8\",\"http://39.134.52.179/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226016/index.m3u8\",\"http://117.156.28.57/PLTV/88888888/224/3221225686/index.m3u8\"]},{\"name\":\"甘肃卫视\",\"source\":[\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/gansu_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/gansu_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/gansu_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/gansu_2/index.m3u8\",\"http://by4.nty.tv189.cn/tm-gansutv-800k.m3u8\",\"http://stream.gstv.com.cn/gsws/sd/live.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225633/index.m3u8\",\"http://117.169.120.141:8080/live/gansustv/.m3u8\"]},{\"name\":\"内蒙古卫视\",\"source\":[\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/neimenggu_2/index.m3u8\",\"http://by4.nty.tv189.cn/tm-neimenggutv-800k.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/neimenggu_2/index.m3u8\",\"http://117.169.120.145:8080/live/neimenggustv/.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225634/index.m3u8\",\"http://183.207.249.7/PLTV/2/224/3221226077/index.m3u8\",\"http://223.110.245.161/ott.js.chinamobile.com/PLTV/3/224/3221225836/index.m3u8\",\"http://39.134.52.159/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226022/index.m3u8\"]},{\"name\":\"宁夏卫视\",\"source\":[\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/ningxia_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/ningxia_2/index.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/ningxia_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/ningxia_2/index.m3u8\",\"http://by4.nty.tv189.cn/tm-ningxiatv-800k.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225632/index.m3u8\",\"http://39.134.52.181/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226034/index.m3u8\",\"http://117.169.120.143:8080/live/ningxiastv/.m3u8\",\"http://223.110.245.151/ott.js.chinamobile.com/PLTV/3/224/3221225842/index.m3u8\"]},{\"name\":\"西藏卫视\",\"source\":[\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/xizang_2/index.m3u8\",\"http://cctvalih5ca.v.myalicdn.com/wstv/xizang_2/index.m3u8\",\"http://by4.nty.tv189.cn/tm-xizangtv-800k.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/xizang_2/index.m3u8\",\"http://cctvbsh5ca.v.live.baishancdnx.cn/wstv/xizang_2/index.m3u8\",\"http://117.169.120.142:8080/live/xizangstv/.m3u8\",\"http://39.134.52.175/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226025/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225638/index.m3u8\"]},{\"name\":\"新疆卫视\",\"source\":[\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/xinjiang_2/index.m3u8\",\"http://by4.nty.tv189.cn/tm-xinjiangtv-800k.m3u8\",\"http://cctvtxyh5ca.liveplay.myqcloud.com/wstv/xinjiang_2/index.m3u8\",\"http://111.40.205.79/PLTV/88888888/224/3221225635/index.m3u8\",\"http://117.169.120.142:8080/live/xinjiangstv/.m3u8\",\"http://39.134.52.175/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226026/index.m3u8\"]}]}"
                Source_obj = JSONObject(Source)
                Source_data = Source_obj.getJSONArray("data")
                get_info()
                Tv_number_max = Source_data.length() - 1
                Source_number_max = Source_adds.length() - 1
                d(TAG, "当前节目共有${Source_number_max}个源")
                d(TAG, "当前共有${Tv_number_max + 1}个节目")
                d(TAG, "当前节目:${Source_name + 1}")
                d(TAG, "当前源:${Source_add}")
                if(Tv_number_max == 0 && Source_number_max == 0){
                    Start_status.text = "您似乎没有添加节目信息呢 w(ﾟДﾟ)w"
                }else{
                    setContentView(R.layout.videos_player)
                    val videoView = findViewById<VideoView>(R.id.video_test)
                    videoView.setVideoURI(Uri.parse("${Source_add}"))
                    videoView.start()
                }
                showinfo()
                closes_info()
            },
            Response.ErrorListener {Start_status.text = "您似乎不在地球上呢!w(ﾟДﾟ)w" })
        network_test.add(stringRequest)
    }


    //滑动监听部分
    override fun onTouchEvent(event: MotionEvent): Boolean {
        setContentView(R.layout.videos_player)
        val videoView = findViewById<VideoView>(R.id.video_test)
        val action = event.actionMasked
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = event.x
                initialY = event.y
            }
            MotionEvent.ACTION_UP -> {
                val finalX = event.x
                val finalY = event.y
                if (initialX - finalX > move && (initialX - finalX) > (initialY - finalY) && (initialX - finalX) > (finalY - initialY)) {
                    d(TAG, "-> Right swipe")
                    if(Source_address_number == Source_number_max) {
                        Source_address_number = 0
                        d(TAG, "调整后的源ID = ${Source_address_number}")
                        get_info()
                        videoView.setVideoURI(Uri.parse("${Source_add}"))
                        videoView.start()
                        showinfo()
                        closes_info()
                    }
                    else if(Source_number_max == 0){
                        makeText(this@MainActivity, "没有更多的源了", LENGTH_SHORT)
                    }
                    else{
                        Source_address_number += 1
                        d(TAG, "调整后的源ID = ${Source_address_number}")
                        d(TAG, "上一个节目源数量 = ${Source_number_max}")
                        get_info()
                        videoView.setVideoURI(Uri.parse("${Source_add}"))
                        videoView.start()
                        showinfo()
                        closes_info()

                    }
                }
                if (finalX - initialX > move && (finalX - initialX) > (initialY - finalY) && (finalX - initialX) > (finalY - initialY)) {
                    d(TAG, "-> Left swipe")
                    if(Source_address_number == 0){
                        Source_address_number = Source_number_max
                        d(TAG, "调整后的源ID(if) = ${Source_address_number}")
                        get_info()
                        videoView.setVideoURI(Uri.parse("${Source_add}"))
                        videoView.start()
                        showinfo()
                        closes_info()
                    }
                    else if(Source_number_max == 0){
                        makeText(this@MainActivity, "没有更多的源了", LENGTH_SHORT)
                    }
                    else{
                        Source_address_number -= 1
                        d(TAG, "调整后的源ID(else) = ${Source_address_number}")
                        get_info()
                        videoView.setVideoURI(Uri.parse("${Source_add}"))
                        videoView.start()
                        showinfo()
                        closes_info()
                    }
                }
                if (finalY - initialY > move && (finalY - initialY) > (initialX - finalX) && (finalY - initialY) > (finalX - initialX)) {
                    d(TAG, "-> Down swipe")
                    if(Tv_number == 0){
                        Source_address_number = 0
                        Tv_number = Tv_number_max
                        get_info()
                        Source_number_max = Source_adds.length() - 1
                        d(TAG, "当前节目:${Source_name}")
                        d(TAG, "当前源:${Source_add}")
                        videoView.setVideoURI(Uri.parse("${Source_add}"))
                        videoView.start()
                        showinfo()
                        closes_info()
                    }else{
                        Source_address_number = 0
                        Tv_number -= 1
                        get_info()
                        Source_number_max = Source_adds.length() - 1
                        d(TAG, "当前节目:${Source_name}")
                        d(TAG, "当前源:${Source_add}")
                        videoView.setVideoURI(Uri.parse("${Source_add}"))
                        videoView.start()
                        showinfo()
                        closes_info()
                    }
                }
                if (initialY - finalY > move && (initialY - finalY) > (initialX - finalX) && (initialY - finalY) > (finalX - initialX)) {
                    d(TAG, "-> Up swipe")
                    if(Tv_number == Tv_number_max){
                        Source_address_number = 0
                        Tv_number = 0
                        get_info()
                        Source_number_max = Source_adds.length() - 1
                        videoView.setVideoURI(Uri.parse("${Source_add}"))
                        videoView.start()
                        showinfo()
                        closes_info()
                    }else{
                        Source_address_number = 0
                        Tv_number += 1
                        get_info()
                        Source_number_max = Source_adds.length() - 1
                        videoView.setVideoURI(Uri.parse("${Source_add}"))
                        videoView.start()
                        showinfo()
                        closes_info()
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    //按键监听部分
    @SuppressLint("ShowToast")
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        setContentView(R.layout.videos_player)
        val videoView = findViewById<VideoView>(R.id.video_test)
        return when (keyCode) {
            KeyEvent.KEYCODE_ENTER -> {
                d(TAG,"->enter")
                d(TAG,"")
                true
            }
            KeyEvent.KEYCODE_DPAD_UP -> {
                if (event.action == KeyEvent.ACTION_UP){
                    d(TAG,"->up")
                    if(Tv_number == Tv_number_max){
                        Source_address_number = 0
                        Tv_number = 0
                        get_info()
                        Source_number_max = Source_adds.length() - 1
                        videoView.setVideoURI(Uri.parse("${Source_add}"))
                        videoView.start()
                        showinfo()
                        closes_info()
                    }else{
                        Source_address_number = 0
                        Tv_number += 1
                        get_info()
                        Source_number_max = Source_adds.length() - 1
                        videoView.setVideoURI(Uri.parse("${Source_add}"))
                        videoView.start()
                        showinfo()
                        closes_info()
                    }
                }
                true
            }
            KeyEvent.KEYCODE_DPAD_DOWN -> {
                d(TAG,"->down")
                if(Tv_number == 0){
                Source_address_number = 0
                Tv_number = Tv_number_max
                get_info()
                Source_number_max = Source_adds.length() - 1
                d(TAG, "当前节目:${Source_name}")
                d(TAG, "当前源:${Source_add}")
                videoView.setVideoURI(Uri.parse("${Source_add}"))
                videoView.start()
                showinfo()
                closes_info()
                }else{
                    Source_address_number = 0
                    Tv_number -= 1
                    get_info()
                    Source_number_max = Source_adds.length() - 1
                    d(TAG, "当前节目:${Source_name}")
                    d(TAG, "当前源:${Source_add}")
                    videoView.setVideoURI(Uri.parse("${Source_add}"))
                    videoView.start()
                    showinfo()
                    closes_info()
                }
                true
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                d(TAG,"-> Left")
                d(TAG, "调整前的源ID = ${Source_address_number}")
                if(Source_address_number == 0){
                    Source_address_number = Source_number_max
                    d(TAG, "调整后的源ID(if) = ${Source_address_number}")
                    get_info()
                    videoView.setVideoURI(Uri.parse("${Source_add}"))
                    videoView.start()
                    showinfo()
                    closes_info()
                }
                else if(Source_number_max == 0){
                    makeText(this@MainActivity, "没有更多的源了", LENGTH_SHORT)
            }
                else{
                    Source_address_number -= 1
                    d(TAG, "调整后的源ID(else) = ${Source_address_number}")
                    get_info()
                    videoView.setVideoURI(Uri.parse("${Source_add}"))
                    videoView.start()
                    showinfo()
                    closes_info()
                }
                true
            }
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                d(TAG,"-> Right")
                d(TAG, "调整前的源ID = ${Source_address_number}")
                d(TAG, "上一个节目源数量 = ${Source_number_max}")
                if(Source_address_number == Source_number_max) {
                    Source_address_number = 0
                    d(TAG, "调整后的源ID = ${Source_address_number}")
                    get_info()
                    videoView.setVideoURI(Uri.parse("${Source_add}"))
                    videoView.start()
                    showinfo()
                    closes_info()
                }
                else if(Source_number_max == 0){
                    makeText(this@MainActivity, "没有更多的源了", LENGTH_SHORT)
                }
                else{
                    Source_address_number += 1
                    d(TAG, "调整后的源ID = ${Source_address_number}")
                    d(TAG, "上一个节目源数量 = ${Source_number_max}")
                    get_info()
                    videoView.setVideoURI(Uri.parse("${Source_add}"))
                    videoView.start()
                    showinfo()
                    closes_info()
                }
                true
            }
            else -> super.onKeyUp(keyCode, event)
        }
    }
}
