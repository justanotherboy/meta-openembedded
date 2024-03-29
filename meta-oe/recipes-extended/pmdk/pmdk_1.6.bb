SUMMARY = "Persistent Memory Development Kit"
DESCRIPTION = "Persistent Memory Development Kit"
HOMEPAGE = "http://pmem.io"
SECTION = "libs"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7db1106255a1baa80391fd2e21eebab7"
DEPENDS = "ndctl"

# Required to have the fts.h header for musl
DEPENDS_append_libc-musl = " fts"

S = "${WORKDIR}/git"

SRC_URI = "git://github.com/pmem/pmdk.git \
           file://0001-jemalloc-jemalloc.cfg-Specify-the-host-when-building.patch \
           file://0002-Makefile-Don-t-install-the-docs.patch \
           file://0003-Makefile-Don-t-build-the-examples.patch \
          "

SRCREV = "695e6eba28c53a69a0ef7bad3cc0f45c21ef3e00"

inherit autotools-brokensep pkgconfig

# Fix jemalloc error:
# | configure: error: cannot run C compiled programs.
# | If you meant to cross compile, use `--host'.
#
# Also fix #warning _FORTIFY_SOURCE requires compiling with optimization (-O) [-Werror=cpp]
EXTRA_OEMAKE = "HOST_SYS='${HOST_SYS}' EXTRA_CFLAGS='${SELECTED_OPTIMIZATION}' LIB_PREFIX=${baselib}"

# Fix the missing fts libs when using musl
EXTRA_OEMAKE_append_libc-musl = " EXTRA_LIBS='-lfts'"

do_install() {
	oe_runmake prefix=${prefix} DESTDIR=${D} install

	# Remove uneeded files
	rm -rf ${D}/usr/lib64/pmdk_debug
}

# Include these by default otherwise the SDK is not very useful
FILES_${PN} += "${bindir}/pmempool ${bindir}/daxio"
FILES_${PN} += "${libdir}/*so*"
FILES_${PN} += "${libdir}/pkgconfig/*.pc"
FILES_${PN} += "${includedir}/libpmemobj++/* ${includedir}/libpmemobj/*"
FILES_${PN} += "/usr/etc"
FILES_${PN} += "/usr/share"

COMPATIBLE_HOST='(x86_64).*'
