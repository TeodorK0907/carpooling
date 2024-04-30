CREATE TABLE roles
(
    role_id   serial primary key,
    role_name varchar(20) not null
);

CREATE TABLE travel_statuses
(
    status_id   serial primary key,
    status_name varchar(20) not null
);

CREATE TABLE comments
(
    comment_id serial primary key,
    content    varchar(250) not null
);

-- todo if float does not work to change to double precision
CREATE TABLE users
(
    user_id         serial primary key,
    username        varchar(20)  not null,
    password        varchar(30)  not null,
    firstname       varchar(20)  not null,
    lastname        varchar(20)  not null,
    email           varchar(50)  not null,
    phone_number    varchar(10)  not null,
    rating          float        not null,
    role_id         int     default 1,
    is_blocked      boolean default false,
    is_archived     boolean default false,
    profile_picture varchar(100) null,

    constraint users_roles_role_id_fk
        foreign key (role_id) references roles (role_id)
);
--todo research Microsoft Bing Maps prior to creating separate tables for locations
CREATE TABLE travels
(
    travel_id        serial primary key,
    starting_point   varchar(250) not null,
    ending_point     varchar(250) not null,
    departure_time   time         not null,
    free_spots       int          not null,
    comment_id       int,
    travel_status_id int          not null,
    constraint travels_comments_comment_id_fk
        foreign key (comment_id) references comments (comment_id),
    constraint travels_travel_statuses_travel_status_id_fk
        foreign key (travel_status_id) references travel_statuses (status_id)
);

CREATE TABLE feedbacks
(
    feedback_id       serial primary key,
    content           varchar(250) not null,
    user_id           int          not null,
    recipient_user_id int          not null,
    constraint feedbacks_users_user_id_fk
        foreign key (user_id) references users (user_id),
    constraint feedbacks_users_recipient_user_id_fk
        foreign key (recipient_user_id) references users (user_id)

)