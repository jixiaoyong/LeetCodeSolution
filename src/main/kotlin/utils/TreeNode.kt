package utils

class TreeNode(var `val`: Int) {
    @JvmField
    var left: TreeNode? = null

    @JvmField
    var right: TreeNode? = null

    override fun toString(): String {
        return "{${`val`}(left:$left,right:$right)}"
    }
}