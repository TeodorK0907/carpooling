CREATE TABLE roles
(
    role_id   serial primary key,
    role_name varchar(20) not null
);

CREATE TABLE travel_statuses
(
    status_id   int primary key,
    status_name varchar(20) not null
);

CREATE TABLE feedbacks
(
    feedback_id     serial primary key,
    avg_feedback    float not null default 0,
    total_feedbacks int   not null default 0
--     user_id           int   not null,
--     recipient_user_id int   not null,
--     constraint feedbacks_users_user_id_fk
--         foreign key (user_id) references users (user_id),
--     constraint feedbacks_users_recipient_user_id_fk
--         foreign key (recipient_user_id) references users (user_id)
);

-- todo if float does not work to change to double precision
CREATE TABLE users
(
    user_id             serial primary key,
    username            varchar(20)  not null,
    password            varchar(30)  not null,
    firstname           varchar(20)  not null,
    lastname            varchar(20)  not null,
    email               varchar(50)  not null,
    phone_number        varchar(10)  not null,
    driver_rating_id    int          not null,
    passenger_rating_id int          not null,
    role_id             int,
    is_blocked          boolean default false,
    is_archived         boolean default false,
    profile_picture     varchar(100) null,
    constraint users_feedbacks_driver_rating_id_fk
        foreign key (driver_rating_id) references feedbacks (feedback_id),
    constraint users_feedbacks_passenger_rating_id_fk
        foreign key (passenger_rating_id) references feedbacks (feedback_id)
);

CREATE TABLE passengers
(
    passenger_id serial primary key,
    user_id      int         not null,
    username     varchar(20) not null,
    travel_id    int         not null,
    gave_rating  boolean default false,
    given_rating boolean default false
);

CREATE TABLE candidates
(
    candidate_id serial primary key,
    user_id      int         not null,
    travel_id    int         not null,
    username     varchar(20) not null
);


--todo try first using points only
CREATE TABLE addresses
(
    address_id   serial primary key,
    address_name varchar(250) not null
);

CREATE TABLE points
(
    point_id  serial primary key,
    address   varchar(250)     not null,
    latitude  double precision not null,
    longitude double precision not null
--     constraint points_addresses_address_fk
--         foreign key (address) references addresses (address_id)
);

CREATE TABLE travels
(
    travel_id        serial primary key,
    created_by       int              not null,
    starting_point   int              not null,
    ending_point     int              not null,
    departure_time   time             not null,
    free_spots       int              not null,
--     comment_id       int,
    travel_status_id int              not null,
    duration         double precision not null,
    distance         double precision not null,
    constraint travels_users_created_by_fk
        foreign key (created_by) references users (user_id),
    constraint travels_points_starting_point_id_fk
        foreign key (starting_point) references points (point_id),
    constraint travels_points_ending_point_id_fk
        foreign key (ending_point) references points (point_id)
--     constraint travels_comments_comment_id_fk
--         foreign key (comment_id) references comments (comment_id),
);
--todo consider if comment needs to have travelId as a foreign key or not
CREATE TABLE comments
(
    comment_id serial primary key,
    travel_id  int          not null,
    content    varchar(250) not null,
    constraint comments_travels_travel_id_fk
        foreign key (travel_id) references travels (travel_id)
            on delete cascade
);

CREATE TABLE travel_candidates
(
    travel_id    int not null,
    candidate_id int not null,
    constraint travel_candidates_travels_travel_id_fk
        foreign key (travel_id) references travels (travel_id) on delete cascade,
    constraint travel_candidates_travels_candidate_id_fk
        foreign key (candidate_id) references candidates (candidate_id) on delete cascade
);

CREATE TABLE travel_passengers
(
    travel_id    int not null,
    passenger_id int not null,
    constraint travel_candidates_travels_travel_id_fk
        foreign key (travel_id) references travels (travel_id) on delete cascade,
    constraint travel_candidates_travels_passenger_id_fk
        foreign key (passenger_id) references passengers (passenger_id) on delete cascade
);