``` sql
create table category (
    id int auto_increment,
    blogger_id int,
    flag_type varchar(15),
    parent_id int,
    primary key(id),
    foreign key(blogger_id) references blogger(id),
    foreign key(parent_id) references category(id)
) engine InnoDB;
```