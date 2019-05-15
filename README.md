# MaterialDesign
Just a Material Design demo

[Material Design 中文版](http://design.1sters.com/)

使用 Material Design 需要使用支持其的主题，API 21 以上的主题
* `android:style/Theme.Material`
* `android:style/Theme.Material.Light`
* `android:style/Theme.Material.Light.DarkActionBar`

如果需要适配API 21以下的设备，需要使用兼容包
* `android:style/Theme.AppCompat.Light`
* `android:style/Theme.AppCompat.Light.DarkActionBar`

## RecyclerView

实现Toolbar跟随RecyclerView滑动效果：
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".recycler.RecyclerViewFragment">

    <com.google.android.material.appbar.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <androidx.appcompat.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?actionBarSize"
            app:layout_scrollFlags="scroll|enterAlways"
            app:title="toolbar" />

    </com.google.android.material.appbar.AppBarLayout>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@android:color/white"
        android:scrollbars="vertical"
        app:layout_behavior="@string/appbar_scrolling_view_behavior"
        tools:listitem="@layout/item_feed" />

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

要是想要实现顶部悬浮条，参考 RecyclerViewFragment.java

## 自定义Behavior
参考 ScaleBehavior.java


## 沉浸式
Android 4.4以下不支持沉浸式
#### Android 4.4 可以下面两种使用
* 方式一：style中设置  
```xml
<item name="android:windowTranslucentStatus">true</item>
<item name="android:windowTranslucentNavigation">true</item>
```
* 方式二：代码设置
```java
getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
```
#### Android 5.0以上可以使用代码设置
```java
Window window = getWindow();
window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// 设置状态栏颜色透明
window.setStatusBarColor(Color.TRANSPARENT);

int visibility = window.getDecorView().getSystemUiVisibility();
// 布局内容全屏展示
visibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
// 隐藏虚拟导航栏
visibility |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
// 防止内容区域大小发生变化
visibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

window.getDecorView().setSystemUiVisibility(visibility);
```

#### 4.4 与 5.0 兼容适配沉浸式
```java
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    Window window = getWindow();
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    // 设置状态栏颜色透明
    window.setStatusBarColor(Color.TRANSPARENT);

    int visibility = window.getDecorView().getSystemUiVisibility();
    // 布局内容全屏展示
    visibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
    // 隐藏虚拟导航栏
    visibility |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    // 防止内容区域大小发生变化
    visibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

    window.getDecorView().setSystemUiVisibility(visibility);
} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
}
```

#### 带 Toolbar 的沉浸式适配
参考 ImmersiveActivity.java

#### 沉浸式 RecyclerView
参考 RecyclerViewActivity.java

注意 android:fitsSystemWindows="true" 的使用，默认为false。  
如果希望布局延伸到状态栏，默认 false 即可，如果不希望与状态栏重叠，则设置为 true。

```xml
<com.google.android.material.appbar.AppBarLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:fitsSystemWindows="true">

    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?actionBarSize"
        android:fitsSystemWindows="true"
        app:layout_scrollFlags="scroll|enterAlways"
        app:title="toolbar" />

</com.google.android.material.appbar.AppBarLayout>
```
注意：fitsSystemWindows 生效前提：当前页面没有标题栏，并且状态栏或者底部导航栏透明。

[android:fitsSystemWindows属性](https://blog.csdn.net/alex01550/article/details/86521132)

## CardView
参考 fragment_cardview.xml