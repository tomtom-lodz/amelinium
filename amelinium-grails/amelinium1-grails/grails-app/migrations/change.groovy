databaseChangeLog = {

	changeSet(author: "hajdi (generated)", id: "1391429945000-1") {
		addColumn(tableName: "project") {
			column(name: "add_new_feature_groups", type: "BOOL") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "hajdi (generated)", id: "1391429945000-2") {
		addColumn(tableName: "project") {
			column(name: "is_cumulative", type: "BOOL") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "hajdi (generated)", id: "1391429945000-3") {
		addColumn(tableName: "project") {
			column(name: "multiline_feature", type: "BOOL") {
				constraints(nullable: "false")
			}
		}
	}
}
