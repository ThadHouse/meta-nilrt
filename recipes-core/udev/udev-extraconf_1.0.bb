SUMMARY = "Extra machine specific configuration files"
DESCRIPTION = "Extra machine specific configuration files for udev, specifically blacklist information."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = " \
       file://automount.rules \
       file://mount.sh \
       file://mount.blacklist \
       file://autonet.rules \
       file://network.sh \
       file://localextra.rules \
       file://50-plugdev.rules \
       file://61-removable-storage-polling.rules \
"

SRC_URI_append_xilinx-zynq = "file://fpga.rules"

do_install() {
    install -d ${D}${sysconfdir}/udev/rules.d

    install -m 0644 ${WORKDIR}/automount.rules     ${D}${sysconfdir}/udev/rules.d/automount.rules
    install -m 0644 ${WORKDIR}/autonet.rules       ${D}${sysconfdir}/udev/rules.d/autonet.rules
    install -m 0644 ${WORKDIR}/localextra.rules    ${D}${sysconfdir}/udev/rules.d/localextra.rules
    install -m 0644 ${WORKDIR}/50-plugdev.rules    ${D}${sysconfdir}/udev/rules.d/50-plugdev.rules
    install -m 0644 ${WORKDIR}/61-removable-storage-polling.rules    ${D}${sysconfdir}/udev/rules.d/61-removable-storage-polling.rules

    install -m 0644 ${WORKDIR}/mount.blacklist     ${D}${sysconfdir}/udev/

    install -d ${D}${sysconfdir}/udev/scripts/

    install -m 0755 ${WORKDIR}/mount.sh ${D}${sysconfdir}/udev/scripts/mount.sh
    install -m 0755 ${WORKDIR}/network.sh ${D}${sysconfdir}/udev/scripts
}

do_install_append_xilinx-zynq() {
    install -m 0644 ${WORKDIR}/fpga.rules    ${D}${sysconfdir}/udev/rules.d/fpga.rules
}

FILES_${PN} = "${sysconfdir}/udev"
RDEPENDS_${PN} = "udev"
CONFFILES_${PN} = "${sysconfdir}/udev/mount.blacklist"

# to replace udev-extra-rules from meta-oe
RPROVIDES_${PN} = "udev-extra-rules"
RREPLACES_${PN} = "udev-extra-rules"
RCONFLICTS_${PN} = "udev-extra-rules"
