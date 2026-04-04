| Tool     | URL                                                  |
| -------- | ---------------------------------------------------- |
| Cockpit  | `http://localhost:8082/camunda/app/cockpit/default`  |
| Tasklist | `http://localhost:8082/camunda/app/tasklist/default` |
| Admin    | `http://localhost:8082/camunda/app/admin/default`    |
| Root     | `http://localhost:8082/camunda`                      |

CREATE TABLE approval_authority_matrix (
    id BIGSERIAL PRIMARY KEY,
    role_code VARCHAR(50),
    min_amount NUMERIC(18,2),
    max_amount NUMERIC(18,2),
    required_approvals INT,
    next_role VARCHAR(50),
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE approval_user_authority (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    role_code VARCHAR(50) NOT NULL,
    branch_code VARCHAR(50),
    active BOOLEAN DEFAULT TRUE
);

ALTER TABLE approval_authority_matrix
ADD CONSTRAINT chk_amount_range
CHECK (max_amount IS NULL OR max_amount >= min_amount);

INSERT INTO approval_authority_matrix
(role_code, min_amount, max_amount, required_approvals, next_role, active)
VALUES
('L1_OFFICER', 0, 100000000, 1, 'L2_MANAGER', true),
('L2_MANAGER', 100000001, 500000000, 1, 'CREDIT_COMMITTEE', true),
('CREDIT_COMMITTEE', 500000001, 2000000000, 2, 'BOARD', true),
('BOARD', 2000000001, NULL, 3, NULL, true);

INSERT INTO approval_user_authority (username, role_code, branch_code)
VALUES
('l1_jakarta_01', 'L1_OFFICER', 'JKT01'),
('l2_jakarta_01', 'L2_MANAGER', 'JKT01'),
('cc_member_01', 'CREDIT_COMMITTEE', 'HO'),
('cc_member_02', 'CREDIT_COMMITTEE', 'HO'),
('board_01', 'BOARD', 'HO');