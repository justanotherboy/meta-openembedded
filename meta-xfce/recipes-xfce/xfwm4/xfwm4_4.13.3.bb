DESCRIPTION="Xfce4 Window Manager"
SECTION = "x11/wm"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d791728a073bc009b4ffaf00b7599855"
DEPENDS = "virtual/libx11 libxfce4ui libwnck3 libxinerama exo-native"

inherit xfce update-alternatives distro_features_check

REQUIRED_DISTRO_FEATURES = "x11"

SRC_URI[md5sum] = "e53081e5928d401604d158429716e699"
SRC_URI[sha256sum] = "12ad274f6662c8afee35fd9b9310e73bd462c423578d448b2d7353e3c8eda6c1"

PACKAGECONFIG ?= " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'epoxy', '', d)} \
    xpresent \
    startup-notification \
"

PACKAGECONFIG[epoxy] = "--enable-epoxy,--disable-epoxy,libepoxy"
PACKAGECONFIG[xpresent] = "--enable-xpresent,--disable-xpresent,libxpresent"
PACKAGECONFIG[startup-notification] = "--enable-startup-notification,--disable-startup-notification,startup-notification"

python populate_packages_prepend () {
    themedir = d.expand('${datadir}/themes')
    do_split_packages(d, themedir, '^(.*)', 'xfwm4-theme-%s', 'XFWM4 theme %s', allow_dirs=True)
}

PACKAGES_DYNAMIC += "^xfwm4-theme-.*"

ALTERNATIVE_${PN} = "x-window-manager"
ALTERNATIVE_TARGET[x-window-manager] = "${bindir}/xfwm4"
ALTERNATIVE_PRIORITY[x-window-manager] = "30"

RDEPENDS_${PN} = "xfwm4-theme-default"
FILES_${PN} += "${libdir}/xfce4/xfwm4/helper-dialog \
                ${datadir}/xfwm4/defaults \
"
