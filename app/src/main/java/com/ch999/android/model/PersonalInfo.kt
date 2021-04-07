package com.ch999.android.model

data class UserInfo(
    var userId: String? = "295",
    var username: String? = "翁乾超",
    var height: String? = "168cm",
    var weight: String? = "70kg",
    var birthday: String? = "1992-10-11",
    var graduatedSchool: String? = "云南大学",
    var graduatedDatetime: String? = "2015-07-15",
    var avatarImageUrl: String? = "https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1363297634,1270998999&fm=26&gp=0.jpg",
    var backgroundImageUrl: String? = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201303%2F18%2F233119quyrec7to3ws3rco.jpg&refer=http%3A%2F%2Fattach.bbs.miui.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620402775&t=e0947f24a8ca32ff301c704a2ddd598c"
)

data class SettingItem(
    val title: String = "Title",
    val subtitle: String = "Subtitle",
    val description: String = "Description"
)