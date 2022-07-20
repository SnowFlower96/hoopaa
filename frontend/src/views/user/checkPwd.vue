<template>
<modal v-model="isAuth" :disabled="true" width="420" height="150">
        <div class="check">
            <input class="check__text" v-model="checkPwd" placeholder="본인 확인을 위해 비밀번호를 입력해 주세요" />
            <button class="check__button" type="button" @click="clickPwd">확인</button>
        </div>
    </modal>
</template>

<script>
import Modal from '@/views/common/Modal.vue';
import tempKey from '@/views/user/tempKey';
import { ref } from 'vue';

export default {
name: 'checkPwd',
    components: {
      Modal
    },
    setup(_, { root }) {
        const isAuth = ref(true);
        const checkPwd = ref('');
        const clickPwd = () => {
            if (checkPwd.value === tempKey) {
                root.$router.push({
                    path: '/myPage/info',
                    query: {
                        [tempKey]: true,
                    },
                })
            }
        };
        return {
            isAuth,
            checkPwd,
            clickPwd,
        };
    },
}
</script>

<style>

</style>vue
