package com.hao.topic.topic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.topic.model.entity.topic.TopicRecord;
import com.hao.topic.model.vo.topic.TopicDataVo;
import com.hao.topic.model.vo.topic.TopicUserRankVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description:
 * Author: Hao
 * Date: 2025/5/4 21:59
 */
@Mapper
public interface TopicRecordMapper extends BaseMapper<TopicRecord> {
    Long getRank(Long userId);


    List<TopicUserRankVo> getCountRank(String topicTime);

    TopicUserRankVo getUserCountRank(String topicTime, Long userId);

    Long countTopicFrequency(String date);

    List<TopicDataVo> countTopicDay15();

    List<TopicDataVo> countUserDay15();

    Long countTopicUserRecord(Long currentId);

    Long getDateRank(Long currentId, String date);

    Long selectMaximumCount(Long currentId);

    Long selectRecentConsecutiveCount(Long currentId);

    List<TopicDataVo> userTopicDateCount(String date, Long currentId);

    Long selectMaxSubject(Long id);

    /**
     * 统计用户刷过的不同题目数量
     * @param userId 用户ID
     * @return 不同题目数量
     */
    Long countDistinctTopicByUserId(Long userId);

    /**
     * 统计用户在指定专题下刷过的不同题目数量
     * @param userId 用户ID
     * @param subjectId 专题ID
     * @return 不同题目数量
     */
    Long countDistinctTopicByUserIdAndSubjectId(Long userId, Long subjectId);

    /**
     * 获取用户在指定专题下刷过的不同题目ID列表
     * @param userId 用户ID
     * @param subjectId 专题ID
     * @return 题目ID列表
     */
    List<Long> selectDistinctTopicIdsByUserIdAndSubjectId(Long userId, Long subjectId);
}
