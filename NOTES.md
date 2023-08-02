# File Servers

## Table of Contents

<!-- TOC -->
* [File Servers](#file-servers)
  * [Table of Contents](#table-of-contents)
  * [What is a file server?](#what-is-a-file-server)
  * [How do file servers work?](#how-do-file-servers-work)
  * [Typical features of a file server](#typical-features-of-a-file-server)
  * [File server protocols](#file-server-protocols)
  * [Creating a server](#creating-a-server)
<!-- TOC -->

## What is a file server?

A file server is a computer responsible for the storage and management of data files so
that other computers on the same network can access the files.

## How do file servers work?

File servers only make a remote file system accessible to clients. They can store any type
of data, meaning file servers can store executables, archives, photos, videos, music, etc.

Generally file servers store data as BLOBs (Binary Large Objects). This means that they
don't perform additional indexing or processing of the files stored on them. There may be
additional services provided by a file server that can provide extra features.

A file server doesn't provide built-in ways to interact with the data, it relies on the 
client to use it.

## Typical features of a file server
File servers typically include additional features to enable multiple users to access them 
simultaneously:

- **Permission management** is used to set who can access which files and who has rights to
edit or delete files.
- **File locking** stops multiple users from editing the same file at the same time.
- **Conflict resolution** maintains data integrity in the event of files being overwritten.
- A **Distributed file system** can make the data redundant and highly available by copying
it to multiple servers at different locations.

## File server protocols
- SMB
- NFS
- FTP/SFTP

## Creating a server