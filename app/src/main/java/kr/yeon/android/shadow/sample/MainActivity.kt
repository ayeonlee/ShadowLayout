package kr.yeon.android.shadow.sample

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kr.yeon.android.shadow.ShadowDrawable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iv_test1.background = ShadowDrawable(
            18f,
            0f,
            6f,
            Color.argb(125, 0, 0, 0),
            Color.TRANSPARENT,
            Color.WHITE,
            24f,
            Color.GRAY,
            1f
        )
        iv_test2.background = ShadowDrawable(
            18f,
            0f,
            6f,
            Color.argb(125, 0, 0, 0),
            Color.TRANSPARENT,
            Color.WHITE,
            24f,
            Color.GRAY,
            1f
        )
    }
}
