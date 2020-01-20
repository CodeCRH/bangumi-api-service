Read me
--
缓存你的bangumi追番数据,定时刷新

your gangumi cache,timed refresh

示例页面 demo :  [LINK](https://www.tokyo3rd.com/s/bangumi)



#### 依赖组件 (dependent)
```
java 1.8
redis 5.0.0
```

#### 配置项 (config)
```
server:
  port: 9999                         #your tomcat port
  connection-timeout: 5000ms         #your tomcat connection timeout

system:
  appname:                         #your bangumi app name
  username:        #your bangumi account
  password:                  #your bangumi password
  timeout: 5000                       #request timeout( 5000 ms = 5s)
  redishost: 127.0.0.1                #your redis host
  redisport: 6379                     #your redis port
  redispassword:                      #your redis password
  rediskey: bangumi                   #your redis keyname
```

#### 运行 (run)
```
java -jar jarname
```

#### 获取 bangumi 数据 (get bangumi data)
##### get json
```
curl http://your host:your port/api/getBangumiData
```
##### reutrn 
```
{
	"subject_id": 262940,
	"largeSubject": "总人口230万人，扩展至东京西部的巨大城市。在这座因学生占其人口约八成，而被称为「学园都市」的城市中，扭曲世界法则引发超常现象的力量——超能力的开发正在进行。\r\n\r\n接受特殊课程〈Curriculum〉，获得了能力的学生们，通过定期的『身体检查〈System Scan〉』，得到从『无能力者〈Level0〉』到『超能力者〈Level5〉』的6个阶段的评价。\r\n\r\n站在其顶点的，是被誉为最强的7人的『超能力者』。\r\n其中的一人，御坂美琴。\r\n作为自由操纵电气的最高位的能力者『电击使〈Electro Master〉』，有着『超电磁炮〈Railgun〉』的别名的她，是名门大小姐学校·常盘台中学的14岁女高中生。后辈『风纪委员〈Judgement〉』白井黒子，憧憬着大小姐的同学初春饰利，以及喜欢都市传说的朋友佐天泪子。\r\n在与这些伙伴们一起度过和平、普通、又稍稍有些变化的学园都市日常生活时，一年一度的大事件正在迫近。\r\n\r\n『大霸星祭』。在7天的举办时间中，能力者们以学校为单位展开激战的巨大体育祭。在这期间，学园都市的一部分向公众开放，面对向全世界转播的热闹活动，每个人都很激动。而谁都没有注意到，有什么在这华丽的舞台背后蠢蠢欲动——。",
	"subject": {
		"summary": "",
		"air_date": "2020-01-10",
		"images": {
			"small": "http://lain.bgm.tv/pic/cover/s/aa/db/262940_z2mQQ.jpg",
			"large": "http://lain.bgm.tv/pic/cover/l/aa/db/262940_z2mQQ.jpg",
			"common": "http://lain.bgm.tv/pic/cover/c/aa/db/262940_z2mQQ.jpg",
			"grid": "http://lain.bgm.tv/pic/cover/g/aa/db/262940_z2mQQ.jpg",
			"medium": "http://lain.bgm.tv/pic/cover/m/aa/db/262940_z2mQQ.jpg"
		},
		"air_weekday": 5,
		"name": "とある科学の超電磁砲T",
		"eps": 25,
		"id": 262940,
		"eps_count": 25,
		"collection": {
			"doing": 1374
		},
		"type": 2,
		"name_cn": "科学的超电磁炮T",
		"url": "http://bgm.tv/subject/262940"
	},
	"ep_status": 1,
	"name": "とある科学の超電磁砲T",
	"simpleSubject": "总人口230万人，扩展至东京西部的巨大城市。在这座因学生占其人口约八成，而被称为「学园都市」的城市中，扭曲世界法则引发超常现象的力量——超能力的开发正在进行。\r\n\r\n接受特殊课程〈Curriculum〉，获得了能力的学生们，通过定期的『身体检查〈System Scan〉』，得到从『无能力者〈Level0〉』到『超能力者〈Level5〉』的6个阶段的评价。\r\n\r\n站在其顶点的，是被誉为最强的7人的『超能力者』。\r\n其中的一人，御坂美琴。\r\n作为自由操纵电气的最高位的能力者『电击使〈Electro Master〉』，有着『超电磁炮〈Railgun〉』的别名的她，是名门大小姐学校·常盘台中学的14岁女高中生。后辈『风纪委员〈Judgement〉』白井黒子，憧憬着大小姐的同学初春饰利，以及喜欢都市传说的朋友佐天泪子。\r\n在与这些伙伴们一起度过和平、普通、又稍稍有些变化的学园都市日常生活时，一年一度的大事件正在迫近。\r\n\r\n『大霸星祭』。在7天的举办时间中，能力者们以学校为单位展开激战的巨大体育祭。在这期间，学园都市的一部分向公众开放，面对向全世界转播的热闹活动，每个人都很激动。而谁都没有注意到，有什么在这华丽的舞台背后蠢蠢欲动——。",
	"progressData": {
		"subject_id": 262940,
		"eps": [{
			"id": 833460,
			"status": {
				"url_name": "watched",
				"cn_name": "看过",
				"id": 2,
				"css_name": "Watched"
			}
		}]
	},
	"vol_status": 0,
	"lasttouch": 1579409563
}
```

#### get Html
```
curl http://your host:your port/api/index
```
#### result
```
<html> ......(html content) </html>
```

#### 使用Api (Use API ) 
##### 登陆(post)
```
URL:https://api.bgm.tv/auth?source=$appName
PostData:username
PostData:password
参数：appName：必填；username：必填，邮箱；password：必填，密码；
返回值：Json(用户信息不存在时返回HTML)，包含个人信息及auth，authEncode等，如果错误，会包含error信息。
```

##### 获取所有收藏内容(Get)[仅在看内容]
```
URL:https://api.bgm.tv/user/$userId/collection?cat=$cat
参数:$userId:必填，用户id；$cat：不明，瞎填都行；
返回值：Json数组，内包含收藏剧集对象，如果没有收藏内容，返回字符串null。
```

##### 获取某用户某部剧观看详细信息(Get)
```
URL:https://api.bgm.tv/user/$userId/progress?subject_id=$subjectId&source=$appName&auth=$authEncode
参数:$userId:必填，用户id；$subjectId：必填，剧ID；$appName:必填；$authEncode,必填。
返回值：Json，包含用户对于该剧的详细信息（仅包含已经观看或操作的剧集），如果没有详细信息，返回null。
```
##### 获取某部剧的摘要描述(Get)
```
URL:https://api.bgm.tv/subject/$subjectId?responseGroup=simple
参数:$subjectId：必填，剧ID;
返回值：Json，包含该剧的摘要信息，如果没有详细信息，返回json code 404。
```

##### 获取某部剧的详细描述(Get)
```
URL:https://api.bgm.tv/subject/$subjectId?responseGroup=large
参数:$subjectId：必填，剧ID;
返回值：Json，包含该剧的详细信息，如果没有详细信息，返回json code 404。
```

##### 鸣谢

样式来自 [梓喵出没](https://www.azimiao.com/)