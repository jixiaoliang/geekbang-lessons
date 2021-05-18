
CREATE TABLE `wac_wave_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `record_date` date DEFAULT NULL COMMENT '日期',
  `owner_id` varchar(100) NOT NULL DEFAULT '' COMMENT '门店/物流中心code',
  `syscode` tinyint(3) NOT NULL DEFAULT '30' COMMENT '30:门店 40:仓库',
  `cost_price_total` decimal(18,6) NOT NULL DEFAULT '0.000000' COMMENT '库存总成本',
  `qty_total` decimal(18,6) NOT NULL DEFAULT '0.000000' COMMENT '库存总数量',
  `unit_cost_total` decimal(18,6) NOT NULL DEFAULT '0.000000' COMMENT '库存平均成本',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_record_owner` (`record_date`,`owner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=136693 DEFAULT CHARSET=utf8mb4 COMMENT='库存成本波动';


INSERT INTO `wac_wave_report` (`id`, `record_date`, `owner_id`, `syscode`, `cost_price_total`, `qty_total`, `unit_cost_total`, `created_time`, `updated_time`)
VALUES
	(136693, '2021-05-18', '100001001', 30, 0.000000, 0.000000, 0.000000, '2021-05-18 22:03:05', '2021-05-18 22:03:40'),
	(136696, '2021-05-19', '100001002', 40, 0.000000, 0.000000, 0.000000, '2021-05-18 22:03:28', '2021-05-18 22:03:43'),
	(136697, '2021-05-18', '100001003', 30, 0.000000, 0.000000, 0.000000, '2021-05-18 22:03:50', '2021-05-18 22:03:53');
