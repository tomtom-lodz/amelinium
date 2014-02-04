databaseChangeLog = {

	changeSet(author: "hajdi (generated)", id: "1391097185517-1") {
		createTable(tableName: "backlog") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "backlogPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "edited_by", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "state", type: "varchar(16)")

			column(name: "text", type: "MEDIUMTEXT") {
				constraints(nullable: "false")
			}

			column(name: "ver", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "hajdi (generated)", id: "1391097185517-2") {
		createTable(tableName: "csv") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "csvPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "edited_by", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "text", type: "MEDIUMTEXT") {
				constraints(nullable: "false")
			}

			column(name: "ver", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "hajdi (generated)", id: "1391097185517-3") {
		createTable(tableName: "project") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "projectPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "created_by", type: "varchar(100)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "edited_by", type: "varchar(100)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(100)") {
				constraints(nullable: "false")
			}

			column(name: "revision_id", type: "bigint")

			column(name: "scope_increase", type: "integer")

			column(name: "sprint_length", type: "integer")

			column(name: "status", type: "varchar(6)")

			column(name: "velocity", type: "integer")
		}
	}

	changeSet(author: "hajdi (generated)", id: "1391097185517-4") {
		createTable(tableName: "revision") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "revisionPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "backlog_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "changed_by", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "comment", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "csv_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "project_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "ver", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "hajdi (generated)", id: "1391097185517-9") {
		createIndex(indexName: "FKED904B197F5F9828", tableName: "project") {
			column(name: "revision_id")
		}
	}

	changeSet(author: "hajdi (generated)", id: "1391097185517-10") {
		createIndex(indexName: "name_uniq_1391097185470", tableName: "project", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "hajdi (generated)", id: "1391097185517-11") {
		createIndex(indexName: "FKF074B7DB24307AEC", tableName: "revision") {
			column(name: "csv_id")
		}
	}

	changeSet(author: "hajdi (generated)", id: "1391097185517-12") {
		createIndex(indexName: "FKF074B7DB39B3B50C", tableName: "revision") {
			column(name: "project_id")
		}
	}

	changeSet(author: "hajdi (generated)", id: "1391097185517-13") {
		createIndex(indexName: "FKF074B7DB65CF3A8C", tableName: "revision") {
			column(name: "backlog_id")
		}
	}

	changeSet(author: "hajdi (generated)", id: "1391097185517-5") {
		addForeignKeyConstraint(baseColumnNames: "revision_id", baseTableName: "project", constraintName: "FKED904B197F5F9828", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "revision", referencesUniqueColumn: "false")
	}

	changeSet(author: "hajdi (generated)", id: "1391097185517-6") {
		addForeignKeyConstraint(baseColumnNames: "backlog_id", baseTableName: "revision", constraintName: "FKF074B7DB65CF3A8C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "backlog", referencesUniqueColumn: "false")
	}

	changeSet(author: "hajdi (generated)", id: "1391097185517-7") {
		addForeignKeyConstraint(baseColumnNames: "csv_id", baseTableName: "revision", constraintName: "FKF074B7DB24307AEC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "csv", referencesUniqueColumn: "false")
	}

	changeSet(author: "hajdi (generated)", id: "1391097185517-8") {
		addForeignKeyConstraint(baseColumnNames: "project_id", baseTableName: "revision", constraintName: "FKF074B7DB39B3B50C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "project", referencesUniqueColumn: "false")
	}

	include file: 'change.groovy'

	include file: 'remember.groovy'
}
