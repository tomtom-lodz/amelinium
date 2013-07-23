#!/usr/bin/python
# -*- coding: utf-8 -*-

import psycopg2
import sys

if len(sys.argv)!=5:
	print "Not enough arguments. Provide arguments: database user password 'yyy-mm-dd HH:MM:SS+TZ' to delete all snapshots older than given date."
	sys.exit(1)

con = None
_database = sys.argv[1]
_user = sys.argv[2]
_password = sys.argv[3]
date = sys.argv[4]
try:
     
    con = psycopg2.connect(database=_database, user=_user, password=_password, host='ec2-54-243-190-152.compute-1.amazonaws.com', port='5432') 
    cur = con.cursor()
  
    cur.execute("DELETE FROM snapshot where dt<'"+date+"'")
    print "Deleting snapshots older than "+date
    
    cur.execute("DELETE FROM backlog where dt<'"+date+"'")
    print "Deleting backlogs older than "+date
    
    cur.execute("DELETE FROM chart where dt<'"+date+"'")
    print "Deleting charts older than "+date
    
    con.commit()
    

except psycopg2.DatabaseError, e:
    
    if con:
        con.rollback()
    
    print 'Error %s' % e    
    sys.exit(1)
    
    
finally:
    
    if con:
        con.close()
