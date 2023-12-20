insert into user (user_id, email, nickname, profile_image, provider, provider_id) values (1, '유저1@gmail.com', '유저1별명', '유저1프로필이미지', '유저1프로바이더', '유저1프로바이더아이디');
insert into user (user_id, email, nickname, profile_image, provider, provider_id) values (2, '유저2@naver.com', '유저2별명', '유저2프로필이미지', '유저2프로바이더', '유저2프로바이더아이디');
insert into user (user_id, email, nickname, profile_image, provider, provider_id) values (3, '유저3@github.com', '유저3별명', '유저3프로필이미지', '유저3프로바이더', '유저3프로바이더아이디');
insert into user (user_id, email, nickname, profile_image, provider, provider_id) values (4, '유저4@gmail.com', '유저4별명', '유저4프로필이미지', '유저4프로바이더', '유저4프로바이더아이디');
insert into user (user_id, email, nickname, profile_image, provider, provider_id) values (5, '유저5@daum.net', '유저5별명', '유저5프로필이미지', '유저5프로바이더', '유저5프로바이더아이디');

insert into problem (level, problem_id, content, title) values (0, 101, '1번 문제 내용', '1번 문제 제목');
insert into problem (level, problem_id, content, title) values (1, 102, '2번 문제 내용', '2번 문제 제목');
insert into problem (level, problem_id, content, title) values (2, 103, '3번 문제 내용', '3번 문제 제목');
insert into problem (level, problem_id, content, title) values (1, 104, '4번 문제 내용', '4번 문제 제목');
insert into problem (level, problem_id, content, title) values (0, 105, '5번 문제 내용', '5번 문제 제목');

insert into custom_directory (custom_directory_id, user_id, directory_name) values (201, 1, '다시 볼 문제');
insert into custom_directory (custom_directory_id, user_id, directory_name) values (202, 1, 'DFS');
insert into custom_directory (custom_directory_id, user_id, directory_name) values (203, 2, '다시 볼 문제');
insert into custom_directory (custom_directory_id, user_id, directory_name) values (204, 3, 'DP');
insert into custom_directory (custom_directory_id, user_id, directory_name) values (205, 4, 'DP');

insert into custom_directory_problem (custom_directory_id, custom_directory_problem_id, problem_id) values (201, 1, 101);
insert into custom_directory_problem (custom_directory_id, custom_directory_problem_id, problem_id) values (201, 2, 102);
insert into custom_directory_problem (custom_directory_id, custom_directory_problem_id, problem_id) values (203, 3, 101);
insert into custom_directory_problem (custom_directory_id, custom_directory_problem_id, problem_id) values (202, 4, 103);
insert into custom_directory_problem (custom_directory_id, custom_directory_problem_id, problem_id) values (204, 5, 104);
insert into custom_directory_problem (custom_directory_id, custom_directory_problem_id, problem_id) values (205, 6, 105);

insert into ide (state, ide_id, problem_id, user_id, usercode) values (1, 301, 101, 1, '유저1의 101번문제 작성코드');
insert into ide (state, ide_id, problem_id, user_id, usercode) values (0, 302, 102, 1, '유저1의 102번문제 작성코드');
insert into ide (state, ide_id, problem_id, user_id, usercode) values (0, 303, 101, 2, '유저2의 101번문제 작성코드');
insert into ide (state, ide_id, problem_id, user_id, usercode) values (1, 304, 103, 3, '유저3의 103번문제 작성코드');
insert into ide (state, ide_id, problem_id, user_id, usercode) values (1, 305, 104, 4, '유저4의 104번문제 작성코드');
insert into ide (state, ide_id, problem_id, user_id, usercode) values (1, 306, 101, 5, '유저5의 105번문제 작성코드');

insert into problem_test_case (problem_id, problem_test_case_id, input, output) values (101, 1, '101번 예제 인풋1', '101번 예제 아웃풋1');
insert into problem_test_case (problem_id, problem_test_case_id, input, output) values (101, 2, '101번 예제 인풋2', '101번 예제 아웃풋2');
insert into problem_test_case (problem_id, problem_test_case_id, input, output) values (101, 3, '101번 예제 인풋3', '101번 예제 아웃풋3');
insert into problem_test_case (problem_id, problem_test_case_id, input, output) values (102, 4, '102번 예제 인풋1', '102번 예제 아웃풋1');
insert into problem_test_case (problem_id, problem_test_case_id, input, output) values (102, 5, '102번 예제 인풋2', '102번 예제 아웃풋2');
insert into problem_test_case (problem_id, problem_test_case_id, input, output) values (103, 6, '103번 예제 인풋1', '103번 예제 아웃풋1');
insert into problem_test_case (problem_id, problem_test_case_id, input, output) values (104, 7, '104번 예제 인풋1', '104번 예제 아웃풋1');
insert into problem_test_case (problem_id, problem_test_case_id, input, output) values (105, 8, '105번 예제 인풋1', '105번 예제 아웃풋1');