# VMbackup

## What is this?

A tool with which VMs can be easily backed up to an external drive.

## How does this work?

It optimizes the data copied by hashing chunks and store this hash index alongside the VM on the external drive. When
running the backup again, it reads the VM from the local disk, hashes the chunks and compares the hash to the hash index
on the external drive. Only chunks with a different hash are copied (that means that the chunk has changed and needs to
be updated). This optimization has a positive effect only if the local disk is faster than the external drive.

## What about hash collisions?

Uh oh.

## Can I use this to back up my production VMs?

I haven't tested it that thoroughly that I would recommend that. But I can't and won't stop you. Please report back how
it went.

## Does this only work with VMs?

No, it works on every file. I just use it for VM backups.

## How to use

You'll need Java 17 on your machine.

```
vm-backup SOURCE TARGET
```

## Author

Moritz Halbritter ([@phxql](https://github.com/phxql))

## License

[GPLv3](LICENSE)
