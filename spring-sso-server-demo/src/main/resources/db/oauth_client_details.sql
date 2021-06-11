
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端id（如：mengxuegu_client',
  `resource_ids` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端密码（要加密后存储)',
  `scope` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端授权范all,write,read)',
  `authorized_grant_types` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '4种授权类型（多个授权类型，用英文逗号分隔',
  `web_server_redirect_uri` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '获取授权码后的回调地址',
  `authorities` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权标识',
  `access_token_validity` int NULL DEFAULT NULL,
  `refresh_token_validity` int NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户端（第三方应用）基本信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('client1', '', '$2a$10$PLvF9OwPek9tB8s8Nfi5neY3P69kXBNk3fw9mx3EF9//UDqpqUDXK', 'MEMBER_READ,MEMBER_WRITE', 'authorization_code,refresh_token', 'http://localhost:9001/login', NULL, 50000, NULL, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('client2', '', '$2a$10$PLvF9OwPek9tB8s8Nfi5neY3P69kXBNk3fw9mx3EF9//UDqpqUDXK', 'MEMBER_READ', 'authorization_code,password,implicit,client_credentials,refresh_token', 'http://localhost:9002/login', NULL, 50000, NULL, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('client', 'product-server', '$2a$10$PLvF9OwPek9tB8s8Nfi5neY3P69kXBNk3fw9mx3EF9//UDqpqUDXK', 'all,PRODUCT_API', 'authorization_code,password,implicit,client_credentials,refresh_token', 'http://www.baidu.com/', NULL, 50000, NULL, NULL, 'true');

SET FOREIGN_KEY_CHECKS = 1;
