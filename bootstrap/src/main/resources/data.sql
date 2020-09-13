
insert into T_ROLE (ROLE_ID,ROLE_NAME)
SELECT 1, 'ADMIN'
WHERE
    NOT EXISTS (
        SELECT ROLE_ID FROM T_ROLE WHERE ROLE_ID = 1
    );

insert into T_ROLE (ROLE_ID,ROLE_NAME)
SELECT 2, 'USER'
WHERE
    NOT EXISTS (
        SELECT ROLE_ID FROM T_ROLE WHERE ROLE_ID = 2
    );