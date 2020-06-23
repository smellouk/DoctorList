package com.doctorlist.common.base

interface Commandable<Cmd : BaseCommand> {
    fun onCommand(cmd: Cmd)
}