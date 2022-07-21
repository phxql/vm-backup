# VMbackup

## What is this?

A tool with which VMs can be easily backed up to an external drive.

## How does this work?

It optimizes the data copied by hashing chunks and store this hash index alongside the VM on the external drive. When
running the backup again, it reads the VM from the local disk, hashes the chunks and compares the hash to the hash index
on the external drive. Only chunks with a different hash are copied (that means that the chunk has changed and needs to
be updated). This optimization has a positive effect only if the local disk is faster than the external drive.

## Can I use this to back up my production VMs?

I haven't tested it that thoroughly that I would recommend that. But I can't and won't stop you. Please report back how
it went.

## Does this only work with VMs?

No, it works on every file. I just use it for VM backups.

## How to use

You'll need Java 17 on your machine.

```
Usage: vm-backup [-dhV] [-a=algorithm] [-b=size] <source> <target>
A tool with which VMs can be easily backed up to an external drive
      <source>            Source file to copy
      <target>            Target file to copy to
  -a, --hash-algorithm=algorithm
                          Hash algorithm to use for the chunks. Valid values:
                            SHA1, SHA224, SHA256, SHA384, SHA512. Default:
                            SHA256
  -b, --block-size=size   Block size to use for the chunks. Can use KiB, MiB,
                            GiB, TiB suffixes. Default: 1MiB
  -d, --debug             Switches on debug logging. Default: false
  -h, --help              Show this help message and exit.
  -V, --version           Print version information and exit.
```

## What about hash collisions?

Uh oh.

## What happens if I change the VM on the external drive without updating the hash index?

Uh oh. Don't do that. Bad. Very bad.

## What hash algorithm is used?

SHA-256 by default. You can change this with the `--hash-algorithm` flag.

## What bloock size is used?

1 MiB by default. You can change this with the `--block-size` flag.

## Author

Moritz Halbritter ([@phxql](https://github.com/phxql))

## License

[GPLv3](LICENSE)
