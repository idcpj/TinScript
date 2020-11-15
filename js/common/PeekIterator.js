const LinkedList = require('linkedlist');

class PeekIterator {

    CACHE_SIZE=10;
    it;
    // 需要putback的元素
    stackPutBacks;
    // 基于时间差UN个Ian的缓存
    queueCache;
    enToken;
    constructor(it,endToken=null) {
        this.it=it
        this.stackPutBacks = new LinkedList()
        this.queueCache = new LinkedList()

        this.enToken = endToken
    }


    peek(){
        if (this.stackPutBacks.length > 0){
            return this.stackPutBacks.head
        }

        // peek 相当于 先 next 出来在放回去
        const val = this.next()
        this.putBack()
        return val
    }

    putBack(){
        if (this.queueCache.length>0){
            this.stackPutBacks.push(this.queueCache.pop())
        }
    }

    hasNext(){
        return this.enToken || !!this.peek()
    }

    next(){
        let val ;
        if (this.stackPutBacks.length>0){
            val = this.stackPutBacks.pop()
        }else{
            val = this.it.next().value
            if (val===undefined){

                const tmp = this.enToken
                this.enToken=null
                return tmp
            }
        }

        // 处理缓存
        while(this.queueCache.length > this.CACHE_SIZE -1){
            this.queueCache.shift()
        }

        this.queueCache.push(val)

        return val

    }

}

module.exports=PeekIterator