SET FOREIGN_KEY_CHECKS=0
TRUNCATE TABLE application
TRUNCATE TABLE interface
TRUNCATE TABLE version
SET FOREIGN_KEY_CHECKS=1

INSERT INTO application (id, `name`, description, contact) VALUES('168a1a96337c41e6b3a61e8c43cdf863', 'Application A', 'This is the first application.', 'John Doe, john.doe@company.com')
INSERT INTO application (id, `name`, description, contact) VALUES('46a9df3fc37a499ea40714c5158d2ac5', 'Application B', 'This is the second application.', 'James Doe, james.doe@company.com')
INSERT INTO application (id, `name`, description, contact) VALUES('ad374016d15d47af8d8296fceb6d51fa', 'Application C', 'This is the third application.', 'John Doe, john.doe@company.com')

INSERT INTO interface (id, `name`, description, `type`, application_id) VALUES('40d2370748e8442a87f0a3f15278b745', 'Restricted REST interface', 'Exposes sensible data for application A.', 0, '168a1a96337c41e6b3a61e8c43cdf863')
INSERT INTO interface (id, `name`, description, `type`, application_id) VALUES('a134c5b50f354897925ab9c04cd5373b', 'Open REST interface', NULL, 0, '168a1a96337c41e6b3a61e8c43cdf863')
INSERT INTO interface (id, `name`, description, `type`, application_id) VALUES('72a60b0420aa4f6d94fd00c582b5ee19', 'GraphQL interface', 'The GraphQL interface for application A.', 1, '168a1a96337c41e6b3a61e8c43cdf863')
INSERT INTO interface (id, `name`, description, `type`, application_id) VALUES('c071fa568143477c8efa1cbcda42bd04', 'GraphQL interface', NULL, 1, '46a9df3fc37a499ea40714c5158d2ac5')
INSERT INTO interface (id, `name`, description, `type`, application_id) VALUES('1660d4df25e34aed8b1483842c9f3f1f', 'Customer Data', 'Exposes customer data.', 0, '46a9df3fc37a499ea40714c5158d2ac5')

