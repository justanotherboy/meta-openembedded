DESCRIPTION = "Cython is a language specially designed for writing Python extension modules. \
It's designed to bridge the gap between the nice, high-level, easy-to-use world of Python \
and the messy, low-level world of C."
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=e23fadd6ceef8c618fc1c65191d846fa"
PYPI_PACKAGE = "Cython"
BBCLASSEXTEND = "native nativesdk"

SRC_URI[md5sum] = "f212574687a52706ff53738a64f91398"
SRC_URI[sha256sum] = "20da832a5e9a8e93d1e1eb64650258956723940968eb585506531719b55b804f"

inherit pypi

RDEPENDS_${PN}_class-target += "\
    ${PYTHON_PN}-misc \
    ${PYTHON_PN}-netserver \
    ${PYTHON_PN}-pkgutil \
    ${PYTHON_PN}-pyparsing \
    ${PYTHON_PN}-setuptools \
    ${PYTHON_PN}-shell \
    ${PYTHON_PN}-xml \
"

RDEPENDS_${PN}_class-nativesdk += "\
    nativesdk-${PYTHON_PN}-misc \
    nativesdk-${PYTHON_PN}-netserver \
    nativesdk-${PYTHON_PN}-pkgutil \
    nativesdk-${PYTHON_PN}-pyparsing \
    nativesdk-${PYTHON_PN}-setuptools \
    nativesdk-${PYTHON_PN}-shell \
    nativesdk-${PYTHON_PN}-xml \
"

do_install_append() {
	# Make sure we use /usr/bin/env python
	for PYTHSCRIPT in `grep -rIl '^#!.*python' ${D}`; do
		sed -i -e '1s|^#!.*|#!/usr/bin/env ${PYTHON_PN}|' $PYTHSCRIPT
	done
}
