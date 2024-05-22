package callOtherModule

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class CallSecondApi {
    fun callSecondModule(){
        CoroutineScope(Dispatchers.IO).launch {

        }
    }
}