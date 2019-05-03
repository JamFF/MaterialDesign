# MaterialDesign
Just a Material Design demo

[Material Design 中文版](http://design.1sters.com/)

需要API 21以上
主题有三个
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
