/*
* @description: 206. 反转链表
* https://leetcode-cn.com/problems/reverse-linked-list/
* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 2021/10/30
*/
class ReverseLinkedList {
    /**
     * 规则就是：将A -> B -> null，改为 B -> A -> null
     * 可见需要将B的next指向A，并且将A的next置为null
     */
    fun reverseList1(head: ListNode?): ListNode? {
        if (head?.next == null) {
            return head
        }
        // next 为已经处理好了的链表
        val next = reverseList(head.next)
        // 将head的next置为null
        head.next = null
        //         将next指向head
        var nextEnd = next
        while (nextEnd?.next != null) {
            nextEnd = nextEnd.next
        }
        nextEnd?.next = head
        return next
    }

    /**
     * 规则就是：将A -> B -> null，改为 B -> A -> null
     * 可见需要将B的next指向A，并且将A的next置为null
     */
    fun reverseList(head: ListNode?): ListNode? {
        if (head?.next == null) {
            return head
        }
        // next 为已经处理好了的链表
        val next = reverseList(head.next)
        // head.next?.next就是上面处理好的链表next的最后一个节点
        head.next?.next = head
        head.next = null
        return next
    }

    /**
     * 规则就是：将A -> B -> null，改为 B -> A -> null
     * 先保存A，如果B不为null，则保存B.next，并且将B指向A
     * 如此反复，直达B.next为null
     */
    fun reverseListWhile(head: ListNode?): ListNode? {
        // 分别保存A和B
        var pre = head
        var current = head?.next

        while (current != null) {
            // 如果B不为null的话，就将B指向A，并且保存B.next
            val next = current.next
            current.next = pre

            // 分别保存“A”和“B”
            // 此时，pre为B->A,current为原先的B.next——即下一个链
            pre = current
            current = next
        }
        return pre
    }

    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }

}