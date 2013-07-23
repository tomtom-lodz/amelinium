Amelinium is a tool for predicting dates of releases based on estimated product backlog.

Project success metric: number of projects using this tool

List of projects using this tool

Development of geocoding web application https://geocoder.tomtom.com/app/view/index

Development of map rendering based on http://routes.tomtom.com

Development of services based on http://routes.tomtom.com

Development of map improvement tool http://www.tomtom.com/mapshare/tools/

The aforementioned version of the tool works with confluence. Therefore backlog and chart contents are provided as wiki-markup text.

Standalone version of Amelinium uses the same wiki-markup syntax, but doesn't use confluence. Created projects (consisting of backlog and chart) are stored in database. Edition history is stored in an SVN-like manner. Charts are plotted with the use of Google Visualization API.

Amelinium Specification - standalone version


Create new project

Sequence:

-provide project's name

-provide backlog content

-provide chart content

Outcome:

-adds page with recalculated backlog (work items spanning two lines treated as specified in the check-box)

-adds page with recalculated chart (prepared as specified in the configuration table)


Rename project

Sequence:

-provide project's name

Outcome:

-changes project name (name is not version-ed, old names are lost)


Delete project

Outcome:

-deletes project from the database together with related snapshots, backlogs and charts


Edit backlog

Sequence:

-edit content of the backlog in the text box

Outcome:

-updates backlog page with new backlog content, first recalculates it

-creates new project revision (with new backlog content and old chart content)

-displays warning on the chart page that backlog was updated, but chart hasn't been recalculated


Edit chart

Sequence:

-edit content of the chart backlog in the text box

Outcome:

-updates chart page with new chart content, first synchronizes it with current backlog content

-creates new project revision (with new chart content and old backlog content)

-displays no warning on the chart page


Revert backlog

Outcome:

-updates backlog page with reverted backlog content

-creates new project revision (with reverted backlog content and most recent chart content, adds information from which backlog revision it was reverted)

-displays warning on the chart page that backlog was reverted, but chart hasn't been recalculated


Revert chart

Outcome:

-updates chart page with reverted chart content

-creates new project revision (with reverted chart content and most recent backlog content, adds information from which chart revision it was reverted)

-displays warning on the backlog page that chart was reverted, but it hasn't been recalculated
