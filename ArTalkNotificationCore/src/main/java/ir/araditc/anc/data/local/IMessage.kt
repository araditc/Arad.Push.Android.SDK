package ir.araditc.anc.data.local

interface IMessage {
    fun MessageReceive(title: String, content: String, payload: String)
}