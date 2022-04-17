## 流程图
// .CodeMirror-linenumber
// .editormd .CodeMirror pre
// .editormd-html-preview,.editormd-preview-container
### 发布文章
``` flow 
start=>start: 开始
end=>end: 结束
primaryEdit=>operation: 编辑标题、内容、封面
start->primaryEdit
postArticle=>condition: 发布文章
primaryEdit->postArticle
postCover=>condition: 初始化封面
postArticle(yes,right)->postCover
serverError=>operation: 服务器错误
postArticle(no,left)->serverError
postResult=>operation: 发布完成
postCover(yes,right)->postResult
postCover(no,left)->serverError
serverError->end
postResult->end
```
<!-- operation1=>operation: 操作框1
operation2=>operation: 操作框2
end=>end: 结束
condition(yes,right)->operation1
condition(no)->operation2
operation1->end
operation2->end -->

## web-site 网页
```
# 无权限可浏览
\index                                      # 网站主页
\home                                       # 网站主页
\article                                    # 网站博文列表
\article\{bloggerName}                      # 博主博文列表
\article\{bloggerName}\{articleLink}        # 网站具体博文
\category                                   # 网站分类列表
\category\{bloggerName}                     # 博主分类列表
\category\{bloggerName}\categoryLink}       # 博主分类博主列表
\chat                                       # 网站聊天
\chat\{bloggerName}                         # 博主权限聊天
\about                                      # 网站信息
\about\{bloggerName}                        # 博主身份信息
\blogger\{bloggerName}                      # 博主主页
\blogger\{bloggerName}\index                # 博主主页
\blogger\{bloggerName}\home                 # 博主主页

\web
\web\article\addArticle                     # 博主增加博文
\web\article\addCover
\web\passport\signup                        # 博主注册
\web\passport\signin                        # 博主登录

```

# web-site 接口
```
\api
\api\article
\api\article\{bloggerName}\{articleLink}
\api\article\page\{pageNum}
```


## 数据库

``` sql
create database if not exists vmblog default
    character set utf8mb4 collate utf8mb4_general_ci;

use vmblog;

create table if not exists blogger (
    id int auto_increment,
    er_name varchar(15) not null,
    er_sex varchar(3) default null,
    er_motto varchar(255) default null,
    er_account varchar(31) not null unique,
    er_password varchar(63) not null,
    sa_salt varchar(63) not null,
    register_date datetime default now(),
    last_login_date datetime default null,
    primary key(id)
) engine InnoDB;

create table if not exists mediae (
    id int auto_increment,
    blogger_id int,
    md_name varchar(63) not null,
    md_digest varchar(63) not null unique,
    flag_type varchar(15) not null,
    post_date datetime default now(),
    primary key(id),
    foreign key(blogger_id) references blogger(id),
    unique index(blogger_id, md_digest)
) engine InnoDB;

create table if not exists article (
    id int auto_increment,
    blogger_id int,
    cover_id int default null,
    title varchar(63) not null,
    link_name varchar(63) not null,
    file_name varchar(63) not null,
    flag_type varchar(15) not null,
    post_date datetime default now(),
    update_date datetime default null,
    top_rank int default 0,
    like_count int default 0,
    dislike_count int default 0,
    vis_count int default 0,
    primary key(id),
    foreign key(blogger_id) references blogger(id),
    foreign key(cover_id) references mediae(id),
    unique index(blogger_id, link_name),
    unique index(blogger_id, file_name)
) engine InnoDB;

create table if not exists er_position (
    id bigint(20) unsigned,
    blogger_id bigint(20) unsigned,
    flag_type varchar(15) not null,
    nation varchar(15) not null,
    province varchar(7) not null,
    begin_date datetime not null,
    primary key(id),
    foreign key(blogger_id) references blogger(id)
) engine InnoDB;

create table if not exists er_contact (
    id bigint(20) unsigned,
    blogger_id bigint(20) unsigned,
    flag_type varchar(15) not null,
    content varchar(67) not null,
    primary key(id),
    foreign key(blogger_id) references blogger(id)
) engine InnoDB;

create table if not exists er_development (
    id bigint(20) unsigned,
    blogger_id bigint(20) unsigned,
    position_id bigint(20) unsigned,
    dev_name varchar(31) not null,
    er_role varchar(15) not null,
    end_date datetime default now(),
    primary key(id),
    foreign key(blogger_id) references blogger(id),
    foreign key(position_id) references er_position(id)
) engine InnoDB;

create table if not exists all_chat (
    id bigint(20) unsigned,
    blogger_id bigint(20) unsigned,
    post_date datetime default now(),
    content varchar(255) not null,
    primary key(id),
    foreign key(blogger_id) references blogger(id)
) engine InnoDB;


create table if not exists resource_link (
    id bigint(20) unsigned,
    article_id bigint(20) unsigned,
    resource_id bigint(20) unsigned,
    primary key(id),
    foreign key(article_id) references article(id),
    foreign key(resource_id) references resource(id)
) engine InnoDB;

create table if not exists category (
    id bigint(20) unsigned,
    blogger_id bigint(20) unsigned,
    type_name varchar(15) not null,
    primary key(id),
    foreign key(blogger_id) references blogger(id)
) engine InnoDB;

create table if not exists category_link (
    id bigint(20) unsigned,
    article_id bigint(20) unsigned,
    category_id bigint(20) unsigned,
    primary key(id),
    foreign key(article_id) references article(id),
    foreign key(category_id) references category(id)
) engine InnoDB;

create table if not exists comment (
    id bigint(20) unsigned,
    from_blogger_id bigint(20) unsigned,
    to_blogger_id bigint(20) unsigned,
    article_id bigint(20) unsigned,
    content varchar(255) not null,
    post_date datetime default now(),
    like_count int default 0,
    dislike_count int default 0,
    primary key(id),
    foreign key(from_blogger_id) references blogger(id),
    foreign key(to_blogger_id) references blogger(id)
) engine InnoDB;

```
 
// blogger information
blogger(id, er_name, er_sex, er_motto, 
    er_account, er_password, register_date, last_login_date)
position(id, blogger_id, type, nation, province, data)
contact(id, blogger_id, type, content)
development(id, blogger_id, position_id, er_role, begin_date, end_date)
chat(id, blogger_id, post_time, content)

// article information
article(id, blogger_id, link_name, post_time, hot-rank, vis_count, file_name)
sources(id, blogger_id, link_name, post_time, file_name)
sources_link(id, article_id, sources_id)
category(id, blogger_id, name)
category_link(id, article_id, category_id)
comment(id, from_blogger_id, to_blogger_id, article_id, post _time, content, like, dislike)



