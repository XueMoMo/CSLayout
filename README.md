# CSLayout

  >1.一个支持所有View圆角的库 当然不仅仅是圆角  同时支持单独配置四个角圆角大小 四边阴影颜色及长度四边剪切长度

  >2.扩展CSLayout 实现了一个共享元素的动画效果不同于Android官方的共享动画 支持圆角变换支持自定义传入参数 自定义Animator效果
  关于动画部分 查看 [https://www.jianshu.com/p/001e6cd66bf9](https://www.jianshu.com/p/001e6cd66bf9)
  
![圆角和阴影效果](/demoImg/1023565322.jpg)

# 使用方式 
### 1 将cslibrary 添加到项目 
#### v1.1.1 之前 

      Step 1. Add it in your root build.gradle at the end of repositories:

                allprojects {
                  repositories {
                    ...
                    maven { url 'https://jitpack.io' }
                  }
                }

      Step 2. Add the dependency

                dependencies {
                        implementation 'com.github.XueMoMo:CSLayout:v1.1.1'
                }
#### v1.1.5 之后  [ ![Download](https://api.bintray.com/packages/xuemomo/android/CSLayout/images/download.svg?version=1.1.6) ](https://bintray.com/xuemomo/android/CSLayout/1.1.6/link)
        dependencies {
            implementation 'com.github.XueMoMo:cslibrary:1.1.5'
        }

### 2.1 布局文件中使用  CSLayout

        <com.eericxu.cslibrary.CSLayout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:layout_width="match_parent"
            android:layout_height="350dp"
            app:cs_corner_overlay="false"
            app:cs_clip="20dp"
            app:cs_corner="20dp"
            app:cs_shadow_size="15dp">
            <ImageView
                android:id="@+id/iv_cover"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:scaleType="centerCrop"
                android:src="@mipmap/img_4" />
        
        
        </com.eericxu.cslibrary.CSLayout>

### 2.2 自定义布局使用  CSHelper
          
    val csHelper = CSHelper()
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        csHelper.initAttr(this,context, attrs)
    }
    
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        csHelper.initAttr(this,context, attrs)
    }
    var isDrawed = false
    override fun draw(canvas: Canvas?) {
        setLayerType(LAYER_TYPE_SOFTWARE,null)
        isDrawed = true
        csHelper.drawBefore(canvas,isInEditMode)
        super.draw(canvas)
        csHelper.drawAfter(canvas,isInEditMode)

    }

    override fun dispatchDraw(canvas: Canvas?) {
        if (isDrawed)
            super.dispatchDraw(canvas)
        else {
            csHelper.drawBefore(canvas,isInEditMode)
            super.dispatchDraw(canvas)
            csHelper.drawAfter(canvas,isInEditMode)
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        csHelper.onSizeChange(this,w, h)
    }
### 3 属性对照

       <!-- *公共属性* -->
    <!--圆形-->
    <attr name="cs_circle" format="boolean" />
    <!--全部圆角半径-->
    <attr name="cs_corner" format="integer|dimension" />
    <!--针对各个角的圆角半径-->
    <attr name="cs_corner_top_left" format="integer|dimension" />
    <attr name="cs_corner_top_right" format="integer|dimension" />
    <attr name="cs_corner_bottom_left" format="integer|dimension" />
    <attr name="cs_corner_bottom_right" format="integer|dimension" />

    <!-- 阴影-->
    <attr name="cs_shadow_size" format="integer|dimension" />       //阴影长度
    <attr name="cs_shadow_color" format="color" />                  //阴影颜色
    //四边阴影大小
    <attr name="cs_shadow_size_left" format="integer|dimension" />  
    <attr name="cs_shadow_size_top" format="integer|dimension" />
    <attr name="cs_shadow_size_right" format="integer|dimension" />
    <attr name="cs_shadow_size_bottom" format="integer|dimension" />

    <!--裁切大小-->
    <attr name="cs_clip" format="integer|dimension" />
    //四边的裁切大小
    <attr name="cs_clip_left" format="integer|dimension" />
    <attr name="cs_clip_top" format="integer|dimension" />
    <attr name="cs_clip_right" format="integer|dimension" />
    <attr name="cs_clip_bottom" format="integer|dimension" />

    <!--覆盖模式 or 裁切模式-->
    <attr name="cs_corner_overlay" format="boolean" />//false 为覆盖模式 默认覆盖模式   true 裁切模式
    <attr name="cs_corner_overlay_color" format="color" />  //覆盖模式时 覆盖的颜色
    

# 注意: 

 ## 阴影是绘制在裁切的边缘上 
 ## 阴影长度按最大阴影大小绘制 通过调整绘制区域控制各边的阴影长度 
 所以要设置阴影需要先设置裁切大小
  
  cs_clip >= cs_shadow_size
  cs_clip_left >= cs_shadow_size_left
  cs_clip_top >= cs_shadow_size_top
  cs_clip_right >= cs_shadow_size_right
  cs_clip_bottom >= cs_shadow_size_bottom
  
# [LICENSE](/LICENSE)  

