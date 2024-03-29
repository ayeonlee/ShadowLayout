package kr.yeon.android.shadow

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

interface ShadowView  {
    // view's original padding
    val originPadding: Rect

    fun initOriginPadding(left: Int, top: Int, right: Int, bottom: Int) {
        originPadding.left = left
        originPadding.top = top
        originPadding.right = right
        originPadding.bottom = bottom
    }

    fun getOriginPaddingTop(): Int {
        return originPadding.top
    }

    fun getOriginPaddingBottom(): Int {
        return originPadding.bottom
    }

    fun getOriginPaddingLeft(): Int {
        return originPadding.left
    }

    fun getOriginPaddingRight(): Int {
        return originPadding.right
    }

    fun optimizePadding()


    fun makeShadowDrawable(context: Context, attrs: AttributeSet?, view: View): ShadowDrawable {

        val shadowBuilder = ShadowDrawable.Builder()

        if (attrs != null) {
            val a = context.obtainStyledAttributes(
                attrs,
                R.styleable.ShadowLayout
            )

            a.getDimension(R.styleable.ShadowLayout_shadowBlurSize, -1f).takeIf {
                it >= 0f
            }?.apply { shadowBuilder.blurSize = this }
            a.getDimension(R.styleable.ShadowLayout_shadowX, 0f).takeIf {
                it >= 0f
            }?.apply { shadowBuilder.x = this }
            a.getDimension(R.styleable.ShadowLayout_shadowY, 0f).takeIf {
                it >= 0f
            }?.apply { shadowBuilder.y = this }

            a.getColor(R.styleable.ShadowLayout_shadowStartColor, 0).takeIf {
                it != 0
            }?.apply { shadowBuilder.startColor = this }
            a.getColor(R.styleable.ShadowLayout_shadowEndColor, 0).takeIf {
                it != 0
            }?.apply { shadowBuilder.endColor = this }

            a.getColor(R.styleable.ShadowLayout_shadowBgColor, 0).takeIf {
                it != 0
            }?.apply { shadowBuilder.bg = this }
            a.getDimension(R.styleable.ShadowLayout_shadowBgRadius, 0f).takeIf {
                it >= 0f
            }?.apply { shadowBuilder.radius = this }

            a.getColor(R.styleable.ShadowLayout_shadowBorderColor, 0).takeIf {
                it != 0
            }?.apply { shadowBuilder.border = this }
            a.getDimension(R.styleable.ShadowLayout_shadowBorderSize, 0f).takeIf {
                it >= 0f
            }?.apply { shadowBuilder.borderSize = this }

            a.recycle()
        }

        return shadowBuilder.build()
    }
}