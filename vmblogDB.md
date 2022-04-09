## 数据库

``` sql
create database if not exists vmblog default
    character set utf8mb4 collate utf8mb4_general_ci;

use vmblog;

create table if not exists blogger (
    id bigint(20) unsigned,
    er_name varchar(15) not null,
    er_sex varchar(3) default null,
    er_motto varchar(255) default null,
    er_account varchar(31) not null unique,
    er_password varchar(31) not null,
    register_date datetime default now(),
    last_login_date datetime default null,
    primary key(id)
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

create table if not exists article (
    id bigint(20) unsigned,
    blogger_id bigint(20) unsigned,
    link_name varchar(31) not null,
    file_name varchar(36) not null,
    post_date datetime default now(),
    hot_rank int default 0,
    vis_count int default 0,
    primary key(id),
    foreign key(blogger_id) references blogger(id)
) engine InnoDB;

create table if not exists resource (
    id bigint(20) unsigned,
    blogger_id bigint(20) unsigned,
    link_name varchar(31) not null,
    file_name varchar(36) not null,
    post_date datetime default now(),
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



