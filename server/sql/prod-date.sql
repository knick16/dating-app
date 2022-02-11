drop database if exists dating_app;
create database dating_app;
use dating_app;
    
-- MAIN TABLES 
create table roles (
	role_id int primary key auto_increment, 
    role_name varchar(50) not null unique
);
    
create table users (
	user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null, 
    email varchar(500) not null unique, 
    latitude decimal(8, 6), -- latitude = [-90, 90] --> allows [-99.999999, 99.999999]
    longitude decimal(9, 6), -- latitude = [-180, 180] --> allows [-999.999999, 999.999999]
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    disabled boolean not null default 0
);

create table user_preferences (
	user_id int primary key unique,
	age int not null,
    user_gender varchar(10) not null,
    preferred_gender varchar(10) not null,
	travel_radius int not null,
    photo varchar(100) null,
	race varchar(15) null,
    ethnicity varchar(25) null,
    religion varchar(20) null
);

create table conversations (
	conversation_id int primary key auto_increment,
    conversation_name varchar(2048) null,
    conversation_type varchar(10) not null,
    time_created timestamp not null default current_timestamp
);

create table messages (
	message_id int primary key auto_increment,
    sender_id int not null,
    conversation_id int not null, 
    text_content varchar(500) not null, 
    image_content varchar(100) null, 
    time_sent timestamp not null default current_timestamp,
    
    constraint fk_messages_sender_id_users_user_id
		foreign key (sender_id)
        references users(user_id), 
	constraint fk_messages_conversation_id_conversations_conversation_id
		foreign key (conversation_id)
        references conversations(conversation_id), 
	
    constraint image_or_text_required -- image and text cannot both be null/empty, but both can exist
		check (not (nullif(text_content,'') is null and nullif(image_content,'') is null) ) 

);

-- BRIDGE TABLES

create table users_roles (
	user_id int not null,
	role_id int not null, 
	
    constraint fk_users_roles_user_id
		foreign key (user_id) 
        references users(user_id),
	constraint fk_users_roles_role_id 
		foreign key (role_id) 
        references roles(role_id),
    
    constraint pk_users_roles primary key (user_id, role_id)
);

create table users_conversations (
	user_id int not null, 
    conversation_id int not null,
    show_photo int default 0,
    
    constraint fk_users_converstaions_user_id
		foreign key (user_id)
        references users(user_id),
	constraint fk_users_converstaions_conversation_id
		foreign key (conversation_id)
        references conversations(conversation_id),
	
    constraint pk_users_conversations primary key (user_id, conversation_id)
);

create table relationships (
	a_id int not null, 
    b_id int not null,
	relationship_type varchar(10) not null,
    
    constraint fk_relationships_a_id 
		foreign key (a_id)
        references users(user_id), 
	constraint fk_relationships_b_id 
		foreign key (b_id)
        references users(user_id),
        
	constraint pk_relationships primary key (a_id, b_id), 
    
    constraint not_relation_with_self 
		check (a_id != b_id)
);

create table requests (
	requestor_id int not null,
    requested_id int not null,
    request_type varchar(10) not null,
    
	constraint fk_relationships_requestor_id 
		foreign key (requestor_id)
        references users(user_id), 
	constraint fk_relationships_requested_id 
		foreign key (requested_id)
        references users(user_id),
        
	constraint pk_relationships primary key (requestor_id, requested_id), 
    
    constraint not_requested_self 
		check (requestor_id != requested_id)
);
    
-- data
    
-- users + roles
insert into users (username, password_hash, email, disabled, latitude, longitude, first_name, last_name)
	values 
	-- admins 
	('k-gavin', '$2a$12$VurollGpuTOEYllyOIRyoOrBXM2/omPCX0kU18n/XZjgiyvkBy6Dy', 'k.gavin@.gavinners.com', FALSE, null, null, 'Klavier', 'Gavin'),
		
	-- users
	('m-edgeworth', '$2a$12$VurollGpuTOEYllyOIRyoOrBXM2/omPCX0kU18n/XZjgiyvkBy6Dy', 'm.edgeworth@steel-samurai.com', FALSE, null, null, 'Miles', 'Edgeworth'),
    ('p-wright', '$2a$12$VurollGpuTOEYllyOIRyoOrBXM2/omPCX0kU18n/XZjgiyvkBy6Dy', 'p.wright@wright-anything.com', FALSE, null, null, 'Phoenix', 'Wright');
    
insert into roles (role_name)
	values
		('ADMIN'), 
		('USER');
	
insert into users_roles (user_id, role_id)
	values 
	-- admins
		(1, 1),
	-- users
		(2, 2),
		(3, 2);
                
-- friends
insert into relationships (a_id, b_id, relationship_type) 
	values 	(2, 3, 'interest'), (3, 2, 'interest'),  
			(1, 2, 'friend'), (2, 1, 'friend'), 
			(1, 3, 'friend'), (3, 1, 'friend');
                
-- conversations
insert into conversations (conversation_name, conversation_type)
	values ('Steel Samurai Fan Club', 'friend'), 
		   ('Miles E. and Phoenix W.', 'interest');
        
insert into users_conversations (user_id, conversation_id)
	values 
		-- Steel Samrurai Fan Club
			(1, 1), 
			(2, 1), 
			(3, 1), 
		-- Miles E. and Pheonix W.
			(2, 2), 
			(3, 2);
                
-- messages
insert into messages (sender_id, conversation_id, text_content) 
		values (1, 1, "Hello there!"),
			   (1, 1, "Did either of you catch last night's epidode?"),
			   (3, 1, "Nah, Trucy dragged me to one of her magic shows. :/"),
			   (2, 1, "I just got the limited edition steel samurai collector's card!"),
               (3, 1, "Mmmmm, I've been meaning to snag one for Maya. Where did you find it?"),
               (2, 1, "I got it from the ice cream shop by your agency. You just need to order a Chunky Monkey Super Duper Banana Split Kids Only No Adults Allowed Fudge Sundae."),
               (1, 1, "That's so you.");
            
-- messages
insert into messages (sender_id, conversation_id, text_content) 
		values (3, 2, "Hi Miles. :)"), 
			   (2, 2, "Hello Wright.");
