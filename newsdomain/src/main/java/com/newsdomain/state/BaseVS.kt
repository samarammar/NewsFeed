package com.newsdomain.state


interface   BaseVS {

    class Loading : BaseVS{
        var type = 0
        companion object {
            fun getWithType(t:Int):Loading{
                var instance = Loading()
                instance.type = t
                return instance
            }
        }
    }
    class Success : BaseVS{
        var type = 0
        var message = ""
        companion object {
            fun getWithType(t:Int):Success{
                var instance = Success()
                instance.type = t
                return instance
            }
        }
    }

    class Error(val error:Throwable) : BaseVS{
        var type = 0
        companion object {
            fun getWithType(error:Throwable,t:Int):Error{
                var errorInstance = Error(error)
                errorInstance.type = t
                return errorInstance
            }
        }
    }

    class Empty : BaseVS{
        var type = 0
        companion object {
            fun getWithType(t:Int):Empty{
                var instance = Empty()
                instance.type = t
                return instance
            }
        }
    }
}