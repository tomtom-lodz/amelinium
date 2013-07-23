package com.tomtom.amelinium.projectservice.model;

import java.util.List;

public interface ProjectDao {

	public Project store(final Project project);

	public Project get(long id);

	public List<Project> getAll();

	public void delete(long id);

}